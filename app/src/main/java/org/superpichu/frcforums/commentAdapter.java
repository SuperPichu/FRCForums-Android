package org.superpichu.frcforums;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
public class commentAdapter extends ArrayAdapter<Comment> {

    public commentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, R.layout.comments, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comments, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.name = (TextView) convertView.findViewById(R.id.firstLine);
            viewHolder.date = (TextView) convertView.findViewById(R.id.secondLine);
            viewHolder.body = (TextView) convertView.findViewById(R.id.body);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Comment comment = getItem(position);
        viewHolder.name.setText(comment.user);
        viewHolder.date.setText(comment.date);
        viewHolder.icon.setImageBitmap(comment.icon);
        viewHolder.body.setMovementMethod(LinkMovementMethod.getInstance());
        viewHolder.body.setText(Html.fromHtml(comment.body));
        notifyDataSetChanged();
        return convertView;
    }
    private static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView date;
        TextView body;
    }
}
