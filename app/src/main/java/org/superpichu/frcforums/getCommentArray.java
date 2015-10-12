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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
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
    private final String USER_AGENT = "Mozilla/5.0";
    public getCommentArray(commentFragment fragment){
        this.fragment = fragment;
    }
    @Override
    protected void onPreExecute(){
        if(!fragment.dialog.isShowing()){
            fragment.dialog.show();
        }

    }
    @Override
    protected void onPostExecute(ArrayList<Comment> comments){
        if(fragment.dialog.isShowing()){
            fragment.dialog.dismiss();
        }
        fragment.adapter = new commentAdapter(fragment.getListView().getContext(),comments);
        fragment.adapter.notifyDataSetChanged();
        fragment.setListAdapter(fragment.adapter);
    }
    @Override
    protected ArrayList<Comment> doInBackground(String[]... params) {
        String[] data = params[0];
        String discussionId = data[0];
        String range = data[1];
        ArrayList<Comment> comments = new ArrayList<Comment>();
        DefaultHttpClient defaultClient = Global.defaultClient;
        HttpGet get = new HttpGet("http://forum.frontrowcrew.com/discussion.json/"+discussionId+"/1"+range);
        CookieHandler.setDefault(new CookieManager());
        get.setHeader("Host", "forum.frontrowcrew.com");
        get.setHeader("User-Agent", USER_AGENT);
        get.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        get.setHeader("Accept-Language", "en-US,en;q=0.5");
        get.setHeader("Cookie", getCookies());
        get.setHeader("Connection", "keep-alive");
        HttpResponse response = null;
        try {
            response = defaultClient.execute(get);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            String result = "";
            String line;
            while ((line = reader.readLine()) != null){
                result += line;
            }
            JSONObject json = new JSONObject(result);
            JSONObject discussion = json.getJSONObject("Discussion");
            int max = discussion.getInt("CountComments");
            String title = discussion.getString("Name");
            JSONArray array = json.getJSONArray("Comments");
            int page = json.getInt("Page");
            if(page == 1){
                Comment first = new Comment();
                first.max = max;
                first.body = parseBody(discussion.getString("Body"));
                first.user = discussion.getString("InsertName");
                first.date = parseDate(discussion.getString("DateInserted"));
                URL url = new URL(discussion.getString("InsertPhoto"));
                first.icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                first.thread = title;
                first.page = page;
                first.raw = discussion.getString("Body");
                first.dID = discussion.getInt("DiscussionID");
                comments.add(first);
            }
            for(int i = 0;i<array.length();i++){
                Comment comment = new Comment();
                comment.id = array.getJSONObject(i).getInt("CommentID");
                comment.body = parseBody(array.getJSONObject(i).getString("Body"));
                comment.user = array.getJSONObject(i).getString("InsertName");
                comment.date = parseDate(array.getJSONObject(i).getString("DateInserted"));
                URL url = new URL(array.getJSONObject(i).getString("InsertPhoto"));
                comment.icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                comment.max = max;
                comment.thread = title;
                comment.page = json.getInt("Page");
                comment.raw = array.getJSONObject(i).getString("Body");
                comment.dID = discussion.getInt("DiscussionID");
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
        parsed = parsed.replace("\n","<br>");

        Document doc = Jsoup.parse(parsed);
        Elements images = doc.select("img[src]");
        for(org.jsoup.nodes.Element image : images){
            String link = image.attr("src");
            image.tagName("a");
            image.removeAttr("src");
            image.attr("href",link);
            image.text("Image");
        }
        Elements quotes = doc.select("blockquote[rel]");
        for(Element quote : quotes){
            String source = quote.attr("rel");
            quote.before(source+" said:");
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

    public String getCookies() {
        return Global.cookies;
    }

    public void setCookies(String cookies) {
        Global.cookies = cookies;
    }
}
