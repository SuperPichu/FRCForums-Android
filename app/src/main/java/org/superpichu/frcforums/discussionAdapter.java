package org.superpichu.frcforums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        // Get the data item for this position
        Discussion discussion = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.discussions, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.firstLine);
        TextView description = (TextView) convertView.findViewById(R.id.secondLine);
        // Populate the data into the template view using the data object
        name.setText(discussion.name);
        description.setText(discussion.description);
        // Return the completed view to render on screen
        return convertView;
    }
}
