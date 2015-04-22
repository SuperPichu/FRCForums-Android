package org.superpichu.frcforums;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chris on 4/1/15.
 */

public class getDiscussionArray extends AsyncTask<String, Void, ArrayList<Discussion>> {
    private discussionFragment fragment;
    private final String USER_AGENT = "Mozilla/5.0";
    public getDiscussionArray(discussionFragment fragment){
        this.fragment = fragment;
    }
    @Override
    protected void onPreExecute(){
        if(!fragment.dialog.isShowing()){
            fragment.dialog.show();
        }
        fragment.dialog.setContentView(R.layout.loading);
        //WebView webView = (WebView)fragment.dialog.findViewById(R.id.webView);
        //webView.setInitialScale(100);
        //webView.loadUrl("file:///android_res/drawable/loading.gif");
        }
    @Override
    protected ArrayList<Discussion> doInBackground(String... params) {
        ArrayList<Discussion> discussions = new ArrayList<Discussion>();
        String range = params[0];
        try{
            DefaultHttpClient defaultClient = Global.defaultClient;
            HttpGet get = new HttpGet("http://forum.frontrowcrew.com/discussions.json?page="+range);
            CookieHandler.setDefault(new CookieManager());
            get.setHeader("Host", "forum.frontrowcrew.com");
            get.setHeader("User-Agent", USER_AGENT);
            get.setHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            get.setHeader("Accept-Language", "en-US,en;q=0.5");
            get.setHeader("Cookie", getCookies());
            get.setHeader("Connection", "keep-alive");
            HttpResponse response = defaultClient.execute(get);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
            String result = reader.readLine();
            JSONObject json = new JSONObject(result);

            if(range.startsWith("1-")){
                JSONArray array1 = json.getJSONArray("Announcements");
                for(int i = 0;i<array1.length();i++) {
                    Discussion discussion = new Discussion();
                    discussion.max = json.getInt("CountDiscussions");
                    discussion.id = array1.getJSONObject(i).getInt("DiscussionID");
                    discussion.name = array1.getJSONObject(i).getString("Name")+" <img src=\"announce.png\">";
                    String firstName = array1.getJSONObject(i).getString("FirstName");
                    String lastName = array1.getJSONObject(i).getString("LastName");
                    URL url = new URL(array1.getJSONObject(i).getString("FirstPhoto"));
                    discussion.icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    discussion.commentMax = array1.getJSONObject(i).getInt("CountComments");
                    if(Global.login){
                        discussion.read = array1.getJSONObject(i).getBoolean("Read");
                        discussion.unreadCount = array1.getJSONObject(i).getInt("CountUnreadComments");
                    }
                    if(!discussion.read) {
                        discussion.description = firstName + "     Most recent by: " + lastName + "\n"+discussion.unreadCount+" unread";
                    }else {
                        discussion.description = firstName + "     Most recent by: " + lastName;
                    }
                    discussions.add(discussion);
                }
            }
            JSONArray array = json.getJSONArray("Discussions");
            for(int i = 0; i<array.length();i++){
                String name="";
                if(array.getJSONObject(i).getInt("Announce") == 1) {
                    name = array.getJSONObject(i).getString("Name") + " <img src=\"announce.png\">";
                }else{
                    name = array.getJSONObject(i).getString("Name");
                }
                Discussion discussion = new Discussion();
                discussion.max = json.getInt("CountDiscussions");
                discussion.id = array.getJSONObject(i).getInt("DiscussionID");
                discussion.name = name;
                String firstName = array.getJSONObject(i).getString("FirstName");
                String lastName = array.getJSONObject(i).getString("LastName");
                if(Global.login){
                    discussion.read = array.getJSONObject(i).getBoolean("Read");
                    discussion.unreadCount = array.getJSONObject(i).getInt("CountUnreadComments");
                }
                if(!discussion.read) {
                    discussion.description = firstName + "     Most recent by: " + lastName + "\n"+discussion.unreadCount+" unread";
                }else {
                    discussion.description = firstName + "     Most recent by: " + lastName;
                }
                URL url = new URL(array.getJSONObject(i).getString("FirstPhoto"));
                discussion.icon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                discussion.commentMax = array.getJSONObject(i).getInt("CountComments");
                discussions.add(discussion);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return discussions;
    }

    @Override
    protected void onPostExecute(ArrayList<Discussion> discussions) {
        if(fragment.dialog.isShowing()){
            fragment.dialog.dismiss();
        }
        fragment.adapter = new discussionAdapter(fragment.getActivity(),discussions,fragment.getResources());
        fragment.adapter.notifyDataSetChanged();
        fragment.setListAdapter(fragment.adapter);
    }

    public String getCookies() {
        return Global.cookies;
    }

    public void setCookies(String cookies) {
        Global.cookies = cookies;
    }
}