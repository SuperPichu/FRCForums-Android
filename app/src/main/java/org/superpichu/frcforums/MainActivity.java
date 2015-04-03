package org.superpichu.frcforums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

//TODO Add loading spinner/gif (make the gif)
//TODO Rewrite UI to use fragment handling and ListView updating
public class MainActivity extends ActionBarActivity {
    ArrayList<Discussion> discussions;
    ArrayList<Comment> comments;
    String range;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        Intent intent = getIntent();
        if(intent.hasExtra("range")){
            range = intent.getStringExtra("range");
        }else{
            range="1-20";
        }
        discussions = new ArrayList<Discussion>();
        try {
            discussions = new getDiscussionArray().execute(range).get();
        }catch (Exception e){
            e.printStackTrace();
        }
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
    public void viewThread(String id){
        String[] data = {id,range};
        ListView listView = (ListView)findViewById(R.id.listView2);
        try {
            comments = new getCommentArray().execute(data).get();
        }catch (Exception e) {
            e.printStackTrace();
        }
        setTitle(comments.get(0).thread);
        CommentAdapter adapter = new CommentAdapter(this,comments);
        listView.setAdapter(adapter);
    }

    public void nextT(View v){
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

    public void lastT(View v){
        Intent intent = new Intent(this,MainActivity.class);
        int end = discussions.get(0).max;
        int start = end - 20;
        range = start+"-"+end;
        intent.putExtra("range",range);
        finish();
        startActivity(intent);
    }

    public void prevT(View v){
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

    public void firstT(View v){
        Intent intent = new Intent(this,MainActivity.class);
        int start = 1;
        int end = start + 20;
        range = start+"-"+end;
        intent.putExtra("range",range);
        finish();
        startActivity(intent);
    }




}
