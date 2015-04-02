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
        super(context,0,discussions);
        resources =resources2;
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
        name.setText(Html.fromHtml(discussion.name, new ImageGetter(),null));
        description.setText(discussion.description);
        icon.setImageBitmap(discussion.icon);
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
            d.setBounds(0,0,64,64);
            return d;
        }
    };

}
