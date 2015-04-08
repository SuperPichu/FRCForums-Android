package org.superpichu.frcforums;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Html;
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
    Resources resources;
    public discussionAdapter(Context context, ArrayList<Discussion> discussions, Resources resources2){
        super(context,R.layout.discussions,discussions);
        resources =resources2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.discussions, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.title = (TextView) convertView.findViewById(R.id.firstLine);
            viewHolder.description = (TextView) convertView.findViewById(R.id.secondLine);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Discussion discussion = getItem(position);
        viewHolder.title.setText(Html.fromHtml(discussion.name,new ImageGetter(),null));
        viewHolder.description.setText(discussion.description);
        viewHolder.icon.setImageBitmap(discussion.icon);
        notifyDataSetChanged();
        return convertView;
    }

    private class ImageGetter implements Html.ImageGetter  {

        public Drawable getDrawable(String source) {
            int id;
            if (source.equals("announce.png")) {
                id = R.drawable.announce;
            }
            else {
                return null;
            }

            Drawable d = resources.getDrawable(id);
            d.setBounds(0,0,240,48);
            return d;
        }
    };


    private static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView description;
    }

}
