package org.superpichu.frcforums;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class writePost extends ActionBarActivity {
    EditText body;
    String dId;
    Dialog dialog;
    String quote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        body = (EditText)findViewById(R.id.body);
        dId = getIntent().getStringExtra("id");
        if(getIntent().hasExtra("quote")){
            quote = getIntent().getStringExtra("quote");
        }
        body.setText(quote);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.posting);
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
            if(Global.login) {
                try {
                    String bodyText = String.valueOf(body.getText());
                    addPost post = new addPost(dialog);
                    String[] data = {dId, bodyText};
                    post.execute(data).get();
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

    public void bold(View view){
        String selected = "";
        if(body.hasSelection()) {
            String text = body.getText().toString();
            selected = text.substring(body.getSelectionStart(), body.getSelectionEnd());
            body.setText(text.substring(0,body.getSelectionStart())+"<b>"+selected+"</b>"+text.substring(body.getSelectionEnd()));
            body.setSelection(body.length());
        }else {
            body.setText(body.getText().toString() + "<b></b>");
            body.setSelection(body.getText().length()-4);
        }
    }

    public void italic(View view){
        String selected = "";
        if(body.hasSelection()) {
            String text = body.getText().toString();
            selected = text.substring(body.getSelectionStart(), body.getSelectionEnd());
            body.setText(text.substring(0,body.getSelectionStart())+"<i>"+selected+"</i>"+text.substring(body.getSelectionEnd()));
            body.setSelection(body.length());
        }else {
            body.setText(body.getText().toString() + "<i></i>");
            body.setSelection(body.getText().length()-4);
        }
    }

    public void underline(View view){
        String selected = "";
        if(body.hasSelection()) {
            String text = body.getText().toString();
            selected = text.substring(body.getSelectionStart(), body.getSelectionEnd());
            body.setText(text.substring(0,body.getSelectionStart())+"<u>"+selected+"</u>"+text.substring(body.getSelectionEnd()));
            body.setSelection(body.length());
        }else {
            body.setText(body.getText().toString() + "<u></u>");
            body.setSelection(body.getText().length()-4);
        }
    }

    public void strike(View view){
        String selected = "";
        if(body.hasSelection()) {
            String text = body.getText().toString();
            selected = text.substring(body.getSelectionStart(), body.getSelectionEnd());
            body.setText(text.substring(0,body.getSelectionStart())+"<del>"+selected+"</del>"+text.substring(body.getSelectionEnd()));
            body.setSelection(body.length());
        }else {
            body.setText(body.getText().toString() + "<del></del>");
            body.setSelection(body.getText().length()-6);
        }
    }

    public void code(View view){
        String selected = "";
        if(body.hasSelection()) {
            String text = body.getText().toString();
            selected = text.substring(body.getSelectionStart(), body.getSelectionEnd());
            body.setText(text.substring(0,body.getSelectionStart())+"<code>"+selected+"</code>"+text.substring(body.getSelectionEnd()));
            body.setSelection(body.length());
        }else {
            body.setText(body.getText().toString() + "<code></code>");
            body.setSelection(body.getText().length()-7);
        }
    }

    public void img(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a URL");
        final EditText input = new EditText(this);
        builder.setView(input)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = input.getText().toString();
                        body.setText(body.getText()+"<img src=\"" + url + "\" />");
                        dialog.dismiss();
                    }
                }).show();
    }

    public void link(View view) {
        if (body.hasSelection()) {
            final String text = body.getText().toString();
            final int start = body.getSelectionStart();
            final int end = body.getSelectionEnd();
            final String selected = text.substring(body.getSelectionStart(), body.getSelectionEnd());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter a URL");
            final EditText input = new EditText(this);
            builder.setView(input)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = input.getText().toString();
                            body.setText(text.substring(0,start ) + "<a href=\"" + url + "\">" + selected + "</a>" + text.substring(end));
                            body.setSelection(end);
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}



