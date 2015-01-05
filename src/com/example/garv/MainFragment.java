package com.example.garv;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class MainFragment extends Fragment {
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	
	TextView tvEmail;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_main, container, false);
		LoginButton authButton = (LoginButton) view
				.findViewById(R.id.authButton);
		//authButton.setReadPermissions(Arrays.asList("public_profile"));
		authButton.setPublishPermissions(new String[] { "email",
			"publish_stream" });
		authButton.setFragment(this);
		
		tvEmail = (TextView) view.findViewById(R.id.tvEmail);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		
		
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@SuppressWarnings("deprecation")
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");

			// Request user data and show the results
			Request.executeMeRequestAsync(session,
					new Request.GraphUserCallback() {

						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							if (user != null) {
								// Display the parsed user info
								Log.i(TAG, buildUserInfoDisplay(user));
								// userInfoTextView.setText(buildUserInfoDisplay(user));
								tvEmail.setText(buildUserInfoDisplay(user));
																

							}
						}
					});

		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
			tvEmail.setText("Hello");
		}
	}

	

	private String buildUserInfoDisplay(GraphUser user) {
		StringBuilder userInfo = new StringBuilder("");

		// Example: typed access (name)
		// - no special permissions required
		userInfo.append(String.format("Name: %s\n\n", user.getName()));
		
		// Example: typed access (email)
		userInfo.append(String.format("Email: %s\n\n", user.asMap().get("email").toString()));

		// Example: typed access (birthday)
		// - requires user_birthday permission
		//userInfo.append(String.format("Birthday: %s\n\n", user.getBirthday()));

		// Example: partially typed access, to location field,
		// name key (location)
		// - requires user_location permission
		// userInfo.append(String.format("Location: %s\n\n",
		// user.getLocation().getProperty("name")));

		// Example: access via property name (locale)
		// - no special permissions required
		// userInfo.append(String.format("Locale: %s\n\n",
		// user.getProperty("locale")));

		// Example: access via key for array (languages)
		// - requires user_likes permission
		/*
		 * JSONArray languages = (JSONArray)user.getProperty("languages"); if
		 * (languages.length() > 0) { ArrayList<String> languageNames = new
		 * ArrayList<String> (); for (int i=0; i < languages.length(); i++) {
		 * JSONObject language = languages.optJSONObject(i); // Add the language
		 * name to a list. Use JSON // methods to get access to the name field.
		 * languageNames.add(language.optString("name")); }
		 * userInfo.append(String.format("Languages: %s\n\n",
		 * languageNames.toString())); }
		 */

		return userInfo.toString();
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
}
