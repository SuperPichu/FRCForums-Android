package org.superpichu.frcforums;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


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
            AccountManager manager = AccountManager.get(this);
            if (manager.getAccountsByType("org.superpichu.frcforums").length > 0){
                Account account = manager.getAccountsByType("org.superpichu.frcforums")[0];
                String user = account.name;
                String pass = manager.getPassword(account);
                String bodyText = String.valueOf(body.getText());
                addPost post = new addPost(dialog);
                String[] data = {dId,bodyText,user,pass};
                try {
                    post.execute(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(this,"You must login first",Toast.LENGTH_SHORT);
            }
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
