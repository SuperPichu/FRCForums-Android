package org.superpichu.frcforums;

import android.content.Context;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;

/**
 * Created by chris on 10/12/15.
 */
public class DiscussionCardAdapter extends CardArrayAdapter{

    public DiscussionCardAdapter(Context context, List<DiscussionCard> cards) {
        super(context,cards);

    }
}
