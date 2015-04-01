package org.superpichu.frcforums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by chris on 4/1/15.
 */
public class CommentAdapter extends ArrayAdapter<Comment>{

    public CommentAdapter(Context context, ArrayList<Comment> comments){
        super(context,0,comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comments, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.firstLine);
        TextView description = (TextView) convertView.findViewById(R.id.secondLine);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        TextView body = (TextView) convertView.findViewById(R.id.body);
        name.setText(comment.user);
        description.setText(comment.date);
        icon.setImageBitmap(comment.icon);
        body.setText(comment.body);
        return convertView;
    }
}
