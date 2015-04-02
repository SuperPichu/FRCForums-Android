package org.superpichu.frcforums;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by chris on 4/1/15.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comments, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.firstLine);
        TextView date = (TextView) convertView.findViewById(R.id.secondLine);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView body = (TextView) convertView.findViewById(R.id.body);
        name.setText(comment.user);
        date.setText(comment.date);
        icon.setImageBitmap(comment.icon);
        body.setMovementMethod(LinkMovementMethod.getInstance());
        body.setText(Html.fromHtml(comment.body));
        return convertView;
    }

}
