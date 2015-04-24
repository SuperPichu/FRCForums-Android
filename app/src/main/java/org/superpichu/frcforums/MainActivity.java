package org.superpichu.frcforums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;


//TODO Finish PM's and implement push notification client.
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
        File xml = new File(getFilesDir().getPath()+"/login.xml");
        if(xml.exists()){
            Credentials credentials = new Credentials();
            try{
                Serializer serializer = new Persister();
                credentials = serializer.read(Credentials.class,xml);
                String[] data = {credentials.getUser(),credentials.getPass()};
                new Login().execute(data).get();
                Global.login = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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
        }else if(id == R.id.action_login){
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnThreadSelected(Discussion item) {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet) {
            commentFragment fragment = (commentFragment) getFragmentManager().findFragmentById(R.id.comments_fragment);
            fragment = (commentFragment) getFragmentManager().findFragmentById(R.id.comments_fragment);
            String range = "#latest";
            fragment.getComments(range, String.valueOf(item.id));
        }else {
            String title = item.name;
            if(title.contains("<")){
                title = title.split("<")[0];
            }
            Intent intent = new Intent(this, org.superpichu.frcforums.discussionView.class);
            intent.putExtra("title", title);
            intent.putExtra("id", String.valueOf(item.id));
            startActivity(intent);
        }
    }
}
