package org.superpichu.frcforums;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chris on 4/1/15.
 */
public class getCommentArray extends AsyncTask<String[], Void, ArrayList<Comment>> {
    private commentFragment fragment;
    public getCommentArray(commentFragment fragment){
        this.fragment = fragment;
    }
    @Override
    protected void onPreExecute(){
        if(!fragment.dialog.isShowing()){
            fragment.dialog.show();
            System.out.println("Show");
        }
        //WebView webView = (WebView)fragment.dialog.findViewById(R.id.webView);
        //webView.setInitialScale(100);
        //webView.loadUrl("file:///android_res/drawable/loading.gif");
    }
    @Override
    protected void onPostExecute(ArrayList<Comment> comments){
        if(fragment.dialog.isShowing()){
            fragment.dialog.dismiss();
            System.out.println("Hide");
        }
        System.out.println("Post");

        fragment.adapter = new commentAdapter(fragment.getActivity(),comments);
        fragment.adapter.notifyDataSetChanged();
        fragment.setListAdapter(fragment.adapter);
    }
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
                first.user = discussion.getString("InsertName");
                first.date = parseDate(discussion.getString("DateInserted"));
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
                comment.date = parseDate(array.getJSONObject(i).getString("DateInserted"));
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
        Pattern pattern = Pattern.compile("([\\s^])https?://.+");
        Matcher matcher = pattern.matcher(body);
        String parsed=body;
        if(matcher.find()){
            for(int i = 0;i<matcher.groupCount();i++){
                String link = matcher.group(i);
                link = link.replace(" ","");
                link = link.replace("\n","");
                parsed = body.replace(link, "<a href=\"" + link + "\">Link</a>");
            }
        }

        Document doc = Jsoup.parse(parsed);
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

    public String parseDate(String preDate){
        String postDate="";
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = format.parse(preDate);
            format = new SimpleDateFormat("h:mma M/dd/yyyy");
            postDate = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return postDate;
    }
}
