package org.superpichu.frcforums;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTagHandler;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by chris on 10/9/15.
 */
public class CommentCard extends Card{
    public Comment comment;
    public CommentCard(Context context,final Comment comment){
        super(context,R.layout.comments);
        this.comment = comment;

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        ImageView icon = (ImageView)view.findViewById(R.id.icon);
        TextView name = (TextView)view.findViewById(R.id.firstLine);
        TextView date = (TextView)view.findViewById(R.id.secondLine);
        TextView body = (TextView)view.findViewById(R.id.body);
        icon.setImageBitmap(comment.icon);
        name.setText(comment.user);
        date.setText(comment.date);
        body.setText(Html.fromHtml(comment.body,null,new HtmlTagHandler()));
        body.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
