package com.example.garv;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

public class MainActivity extends FragmentActivity {
	private MainFragment mainFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	    }
		
		try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.garv", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64
                        .encodeToString(md.digest(), Base64.DEFAULT);

                    Log.e("MY KEY HASH:", sign);

            }
        } catch (NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
		
		
		
		
		/*
		Log.d("started", "started");
		
		try {
			JSONObject toSend = new JSONObject();
			toSend.put("msg", "hello by andro to php project");

			JSONTransmitter transmitter = new JSONTransmitter();
			transmitter.execute(new JSONObject[] { toSend });

		} catch (JSONException e) {
			e.printStackTrace();
		}
		*/
	}

	
}
