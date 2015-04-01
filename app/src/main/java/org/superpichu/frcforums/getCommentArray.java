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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chris on 4/1/15.
 */
public class getCommentArray extends AsyncTask<String, Void, ArrayList<Comment>> {
    @Override
    protected ArrayList<Comment> doInBackground(String... params) {
        String discussionId = params[0];
        ArrayList<Comment> comments = new ArrayList<Comment>();
        DefaultHttpClient defaultClient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://forum.frontrowcrew.com/discussion.json?DiscussionID="+discussionId);
        HttpResponse response = null;
        try {
            response = defaultClient.execute(get);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            String result = reader.readLine();
            JSONObject json = new JSONObject(result);
            JSONArray array = json.getJSONArray("Comments");
            for(int i = 0;i<array.length();i++){
                Comment comment = new Comment();
                comment.id = array.getJSONObject(i).getInt("CommentID");
                comment.body = array.getJSONObject(i).getString("Body");
                comment.user = array.getJSONObject(i).getString("InsertName");
                comment.date = array.getJSONObject(i).getString("DateInserted");
                URL url = new URL(array.getJSONObject(i).getString("InsertPhoto"));
                comment.icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                comments.add(comment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }
}
