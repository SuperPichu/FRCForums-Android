package org.superpichu.frcforums;

import android.app.ListActivity;
import android.app.Notification;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class discussionView extends ActionBarActivity {
    ArrayList<Comment> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_view);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        ListView listView = (ListView)findViewById(R.id.listView);
        try {
            comments = new getCommentArray().execute(id).get();
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(comments.size());
        CommentAdapter adapter = new CommentAdapter(this,comments);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discussion_view, menu);
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
    }
}
