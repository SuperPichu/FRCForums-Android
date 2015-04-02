package org.superpichu.frcforums;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//TODO Add loading spinner/gif
public class MainActivity extends ActionBarActivity {
    ArrayList<Discussion> discussions;
    String range;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.listView);
        discussionAdapter adapter = new discussionAdapter(this,discussions,getResources());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                String discussion = String.valueOf(discussions.get(position).id);
                viewThread(discussion);
            }

        });

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
    public void viewThread(String id){
        Intent intent = new Intent(this,discussionView.class);
        intent.putExtra("id",id);
        intent.putExtra("range","1-20");
        startActivity(intent);
    }

    public void next(View v){
        Intent intent = new Intent(this,MainActivity.class);
        int max = discussions.get(0).max;
        int start = Integer.parseInt(range.split("-")[1]);
        if(start == max){
            start = max - 20;
        }
        int end = start+20;
        if(end > max){
            end = max;
        }
        start++;
        range = start+"-"+end;
        intent.putExtra("range",range);
        finish();
        startActivity(intent);
    }

    public void last(View v){
        Intent intent = new Intent(this,MainActivity.class);
        int end = discussions.get(0).max;
        int start = end - 20;
        range = start+"-"+end;
        intent.putExtra("range",range);
        finish();
        startActivity(intent);
    }

    public void prev(View v){
        Intent intent = new Intent(this,MainActivity.class);
        int end = Integer.parseInt(range.split("-")[0]);
        if(end <= 1){
            end = 21;
        }
        int start = end - 20;
        if(start < 1){
            start = 1;
        }
        end--;
        range = start+"-"+end;
        intent.putExtra("range",range);
        finish();
        startActivity(intent);
    }

    public void first(View v){
        Intent intent = new Intent(this,MainActivity.class);
        int start = 1;
        int end = start + 20;
        range = start+"-"+end;
        intent.putExtra("range",range);
        finish();
        startActivity(intent);
    }




}
