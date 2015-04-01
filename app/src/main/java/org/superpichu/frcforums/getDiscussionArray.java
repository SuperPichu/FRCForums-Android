package org.superpichu.frcforums;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by chris on 4/1/15.
 */
public class getDiscussionArray extends AsyncTask<String, Void, ArrayList<Discussion>>{

    @Override
    protected ArrayList<Discussion> doInBackground(String... params) {
        ArrayList<Discussion> discussions = new ArrayList<Discussion>();
        try{
            DefaultHttpClient defaultClient = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://forum.frontrowcrew.com/api/v1/discussions/list.json");
            HttpResponse response = defaultClient.execute(get);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            String result = reader.readLine();
            JSONObject json = new JSONObject(result);
            JSONArray array = json.getJSONArray("Discussions");
            for(int i = 0; i<array.length();i++){
                int id = array.getJSONObject(i).getInt("DiscussionID");
                String name = array.getJSONObject(i).get("Name").toString();
                String firstName = array.getJSONObject(i).get("FirstName").toString();
                String lastName = array.getJSONObject(i).get("LastName").toString();
                String description = firstName + "     Most recent by: " + lastName;
                discussions.add(new Discussion(name,description,id));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return discussions;
    }
}
