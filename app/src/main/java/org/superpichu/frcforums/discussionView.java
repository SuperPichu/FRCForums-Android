package org.superpichu.frcforums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import java.util.ArrayList;


public class discussionView extends ActionBarActivity {
    ArrayList<Comment> comments;
    String id,range;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_view);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        range = intent.getStringExtra("range");
        String[] data = {id,range};
        //ListView listView = (ListView)findViewById(R.id.listView2);
        try {
            //comments = new getCommentArray().execute(data).get();
        }catch (Exception e) {
            e.printStackTrace();
        }
        setTitle(comments.get(0).thread);
        commentAdapter adapter = new commentAdapter(this,comments);
        //listView.setAdapter(adapter);
    }


   /* @Override
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
    }*/

    public void next(View v){
        Intent intent = new Intent(this,discussionView.class);
        intent.putExtra("id",id);
        int max = comments.get(0).max;
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
        Intent intent = new Intent(this,discussionView.class);
        intent.putExtra("id",id);
        int end = comments.get(0).max;
        int start = end - 20;
        range = start+"-"+end;
        intent.putExtra("range",range);
        finish();
        startActivity(intent);
    }

    public void prev(View v){
        Intent intent = new Intent(this,discussionView.class);
        intent.putExtra("id",id);
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
        Intent intent = new Intent(this,discussionView.class);
        intent.putExtra("id",id);
        int start = 1;
        int end = start + 20;
        range = start+"-"+end;
        intent.putExtra("range",range);
        finish();
        startActivity(intent);
    }
}
