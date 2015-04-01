package org.superpichu.frcforums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chris on 4/1/15.
 */
public class discussionAdapter extends ArrayAdapter<Discussion>{

    public discussionAdapter(Context context, ArrayList<Discussion> discussions){
        super(context,0,discussions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Discussion discussion = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.discussions, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.firstLine);
        TextView description = (TextView) convertView.findViewById(R.id.secondLine);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        name.setText(discussion.name);
        description.setText(discussion.description);
        icon.setImageBitmap(discussion.icon);
        return convertView;
    }
}
