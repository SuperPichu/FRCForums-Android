package org.superpichu.frcforums;

import android.app.Dialog;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 4/9/15.
 */
public class addPost extends AsyncTask<String[], Void, Void> {

    private HttpClient client = Global.defaultClient;
    private final String USER_AGENT = "Mozilla/5.0";
    Dialog dialog;
    public addPost(Dialog dialog){
        this.dialog = dialog;
    }

    @Override
    protected void onPreExecute(){
        dialog.show();
    }
    @Override
    protected void onPostExecute(Void result){
        if(dialog.isShowing()){
            dialog.hide();
        }
    }
    @Override
    protected Void doInBackground(String[]... params) {
        String id = params[0][0];
        String body = params[0][1];
        String url = "http://forum.frontrowcrew.com/entry/signin";
        String postUrl = "http://forum.frontrowcrew.com/vanilla/post/comment/?discussionid=";
        // make sure cookies is turn on
        try {
            CookieHandler.setDefault(new CookieManager());
            String page2 = GetPageContent("http://forum.frontrowcrew.com/discussion/" + id+"/");
            System.out.print(page2);
            List<NameValuePair> postParams = getPostParams(page2, id, body);
            postUrl = postUrl + id;
            makePost(postUrl, postParams);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private void makePost(String url, List<NameValuePair> postParams)
            throws Exception {
        HttpPost post = new HttpPost(url);
        // add header
        post.setHeader("Host", "forum.frontrowcrew.com");
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Accept",
                "application/json, text/javascript, */*; q=0.01");
        post.setHeader("Accept-Language", "en-US,en;q=0.8");
        post.setHeader("Cookie", getCookies());
        post.setHeader("Connection", "keep-alive");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader("Origin","http://forum.frontrowcrew.com");
        post.setHeader("X-Requested-With", "XMLHttpRequest");
        post.setEntity(new UrlEncodedFormEntity(postParams));
        HttpResponse response = client.execute(post);
        int responseCode = response.getStatusLine().getStatusCode();



        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }


    }

    private String GetPageContent(String url) throws Exception {

        HttpGet request = new HttpGet(url);

        request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Language", "en-US,en;q=0.5");

        HttpResponse response = client.execute(request);
        int responseCode = response.getStatusLine().getStatusCode();

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
            System.out.println(line);
        }
        // set cookies
        setCookies(response.getFirstHeader("Cookie") == null ? "" :
                response.getFirstHeader("Cookie").toString());
        return result.toString();

    }

    public List<NameValuePair> getPostParams(String html,String id, String body)
            throws UnsupportedEncodingException {


        Document doc = Jsoup.parse(html);
        Element form = doc.getElementById("Form_Comment");
        Elements inputElements = form.getElementsByTag("input");

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");
            paramList.add(new BasicNameValuePair(key, value));

        }
        paramList.add(new BasicNameValuePair("DeliveryType","VIEW"));
        paramList.add(new BasicNameValuePair("DeliveryMethod","JSON"));
        paramList.add(new BasicNameValuePair("Body",body));
        return paramList;
    }

    public String getCookies() {
        return Global.cookies;
    }

    public void setCookies(String cookies) {
        Global.cookies = cookies;
    }
}
