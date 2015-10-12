package org.superpichu.frcforums;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by chris on 4/1/15.
 */
public class DiscussionCard extends Card{
    public Discussion discussion;
    private Resources resources;
    public DiscussionCard(final Context context, final Discussion discussion) {
        super(context, R.layout.discussions);
        this.discussion = discussion;
        this.resources = context.getResources();

    }

    public DiscussionCard(final Context context, final Discussion discussion, final FragmentManager fragmentManager) {
        super(context, R.layout.discussions);
        this.discussion = discussion;
        this.resources = context.getResources();

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        ImageView icon = (ImageView) parent.findViewById(R.id.icon);
        icon.setImageBitmap(discussion.icon);
        TextView title = (TextView) parent.findViewById(R.id.firstLine);
        title.setText(Html.fromHtml(discussion.name, new ImageGetter(), null));
        TextView description = (TextView) parent.findViewById(R.id.secondLine);
        description.setText(discussion.description);

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
            d.setBounds(0,0,120,24);
            return d;
        }
    };
}
