package com.example.garv;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


public class JSONTransmitter extends AsyncTask<JSONObject, JSONObject, JSONObject> {
 
    String url = "http://doorstepmoney.co.in/android_contacts/andro_php_test.php";
 
    @Override
    protected JSONObject doInBackground(JSONObject... data) {
        JSONObject json = data[0];
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 100000);
 
        JSONObject jsonResponse = null;
        HttpPost post = new HttpPost(url);
        try {
            StringEntity se = new StringEntity("json="+json.toString());
            post.addHeader("content-type", "application/x-www-form-urlencoded");
            post.setEntity(se);
 
            HttpResponse response;
            response = client.execute(post);
            String resFromServer = org.apache.http.util.EntityUtils.toString(response.getEntity());
 
            jsonResponse=new JSONObject(resFromServer);
            Log.i("Response from server", jsonResponse.getString("msg"));
            Log.d("Response from server", jsonResponse.getString("msg"));
        } catch (Exception e) { e.printStackTrace();}
 
        return jsonResponse;
    }
 
}
