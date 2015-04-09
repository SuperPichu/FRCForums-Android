package org.superpichu.frcforums;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;


public class writePost extends ActionBarActivity {
    EditText body;
    String dId;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        body = (EditText)findViewById(R.id.body);
        dId = getIntent().getStringExtra("id");
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_post) {
            File xml = new File(getFilesDir().getPath() + "/login.xml");
            if(xml.exists()) {
                Credentials credentials  = new Credentials();
                try {
                    Serializer serializer = new Persister();
                    credentials = serializer.read(Credentials.class,xml);
                    String bodyText = String.valueOf(body.getText());
                    addPost post = new addPost(dialog);
                    String[] data = {dId, bodyText, credentials.getUser(), credentials.getPass()};
                    post.execute(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }else{
                Toast.makeText(this,"Login First!",Toast.LENGTH_LONG);
            }
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
