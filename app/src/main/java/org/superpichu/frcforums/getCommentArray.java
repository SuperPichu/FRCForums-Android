package org.superpichu.frcforums;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.renderscript.Element;
import android.text.util.Linkify;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chris on 4/1/15.
 */
public class getCommentArray extends AsyncTask<String[], Void, ArrayList<Comment>> {
    @Override
    protected ArrayList<Comment> doInBackground(String[]... params) {
        String[] data = params[0];
        String discussionId = data[0];
        String range = data[1];
        ArrayList<Comment> comments = new ArrayList<Comment>();
        DefaultHttpClient defaultClient = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://forum.frontrowcrew.com/discussion.json?DiscussionID="+discussionId+"&page="+range);
        HttpResponse response = null;
        try {
            response = defaultClient.execute(get);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            String result = reader.readLine();
            JSONObject json = new JSONObject(result);
            JSONObject discussion = json.getJSONObject("Discussion");
            int max = discussion.getInt("CountComments");
            String title = discussion.getString("Name");
            JSONArray array = json.getJSONArray("Comments");
            if(range.startsWith("1-")){
                Comment first = new Comment();
                first.max = max;
                first.body = parseBody(discussion.getString("Body"));
                //first.body = discussion.getString("Body");
                first.user = discussion.getString("InsertName");
                first.date = discussion.getString("DateInserted");
                URL url = new URL(discussion.getString("InsertPhoto"));
                first.icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                first.thread = title;
                comments.add(first);
            }
            for(int i = 0;i<array.length();i++){
                Comment comment = new Comment();
                comment.id = array.getJSONObject(i).getInt("CommentID");
                comment.body = parseBody(array.getJSONObject(i).getString("Body"));
                //comment.body = array.getJSONObject(i).getString("Body");
                comment.user = array.getJSONObject(i).getString("InsertName");
                comment.date = array.getJSONObject(i).getString("DateInserted");
                URL url = new URL(array.getJSONObject(i).getString("InsertPhoto"));
                comment.icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                comment.max = max;
                comment.thread = title;
                comments.add(comment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return comments;
    }

    public String parseBody(String body) {
        Pattern pattern = Pattern.compile("(http://)\\S+");
        Matcher matcher = pattern.matcher(body);
        if(matcher.find()){
            for(int i = 0;i<matcher.groupCount();i++){
                //String link = matcher.group
            }
        }

        //TODO Add links to plain text part. Maybe with regex....

        Document doc = Jsoup.parse(body);
        Elements images = doc.select("img[src]");
        for(org.jsoup.nodes.Element image : images){
            String link = image.attr("src");
            image.tagName("a");
            image.removeAttr("src");
            image.attr("href",link);
            image.text("Image");
        }
        return doc.html();
    }
}
