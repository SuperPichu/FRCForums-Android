package org.superpichu.frcforums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

//TODO Add portrait layout
public class MainActivity extends ActionBarActivity implements discussionFragment.OnThreadSelectedListener {
    ArrayList<Discussion> discussions;
    ArrayList<Comment> comments;
    String range;
    discussionAdapter adapter;
    ListView discussionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        range = "1-20";
        String id = "902";
        setContentView(R.layout.main_view);
    }


    @Override
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
        if (id == R.id.action_refresh) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("range",range);
            finish();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnThreadSelected(String id) {
        System.out.println(id);
        commentFragment fragment = (commentFragment)getFragmentManager().findFragmentById(R.id.comments_fragment);
        fragment.getComments("1-20",id);
    }
}
