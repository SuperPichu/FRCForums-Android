package org.superpichu.frcforums;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
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
                String name = array.getJSONObject(i).getString("Name");
                String firstName = array.getJSONObject(i).getString("FirstName");
                String lastName = array.getJSONObject(i).getString("LastName");
                String description = firstName + "     Most recent by: " + lastName;
                URL url = new URL(array.getJSONObject(i).getString("FirstPhoto"));
                Bitmap icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                discussions.add(new Discussion(name,description,id,icon));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return discussions;
    }
}
