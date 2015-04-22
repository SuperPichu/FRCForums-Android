package org.superpichu.frcforums;

import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

/**
 * Created by chris on 4/1/15.
 */
public class commentAdapter extends ArrayAdapter<Comment> {

    public commentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, R.layout.comments, comments);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comments, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.name = (TextView) convertView.findViewById(R.id.firstLine);
            viewHolder.date = (TextView) convertView.findViewById(R.id.secondLine);
            viewHolder.body = (HtmlTextView) convertView.findViewById(R.id.body);
            Comment comment = getItem(position);
            viewHolder.name.setText(comment.user);
            viewHolder.date.setText(comment.date);
            viewHolder.icon.setImageBitmap(comment.icon);
            viewHolder.body.setHtmlFromString(comment.body,false);
            viewHolder.body.setMovementMethod(LinkMovementMethod.getInstance());
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
            Comment comment = getItem(position);
            viewHolder.name.setText(comment.user);
            viewHolder.date.setText(comment.date);
            viewHolder.icon.setImageBitmap(comment.icon);
            viewHolder.body.setHtmlFromString(comment.body, false);
            viewHolder.body.setMovementMethod(LinkMovementMethod.getInstance());
        }
        notifyDataSetChanged();
        Button quote = (Button) convertView.findViewById(R.id.quote);
        quote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = getItem(position);
                try{
                    Intent intent = new Intent(getContext(), writePost.class);
                    intent.putExtra("id",String.valueOf(comment.dID));
                    String quote = "<blockquote class=\"Quote\" rel=\""+comment.user+"\">"+comment.raw+"</blockquote>";
                    intent.putExtra("quote",quote);
                    getContext().startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }
    private static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView date;
        HtmlTextView body;
    }
}
