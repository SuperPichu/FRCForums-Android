package org.superpichu.frcforums;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardListView;


public class discussionCardFragment extends Fragment {
    public ArrayList<Discussion> discussions;
    public String range;
    public discussionAdapter adapter;
    public Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        return inflater.inflate(
                R.layout.activity_main, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button next = (Button)view.findViewById(R.id.next);
        Button prev = (Button)view.findViewById(R.id.prev);
        Button first = (Button)view.findViewById(R.id.first);
        Button last = (Button)view.findViewById(R.id.last);
        next.setOnClickListener(nextAction);
        prev.setOnClickListener(prevAction);
        first.setOnClickListener(firstAction);
        last.setOnClickListener(lastAction);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            range = "1-20";
            getDiscussions();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public View.OnClickListener nextAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Discussion item = discussions.get(0);
                int max = item.max;
                int start = Integer.parseInt(range.split("-")[1]);
                if (start == max) {
                    start = max - 20;
                }
                int end = start + 20;
                if (end > max) {
                    end = max;
                }
                start++;
                range = start + "-" + end;
                getDiscussions();

            } catch (Exception e) {
            }
        }
    };
    public View.OnClickListener prevAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                int end = Integer.parseInt(range.split("-")[0]);
                if (end <= 1) {
                    end = 21;
                }
                int start = end - 20;
                if (start < 1) {
                    start = 1;
                }
                end--;
                range = start + "-" + end;
                getDiscussions();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public View.OnClickListener firstAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                range = "1-21";
                getDiscussions();
            } catch (Exception e) {
            }

        }
    };

    public View.OnClickListener lastAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Discussion item = discussions.get(0);
                int end = item.max;
                int start = end - 20;
                range = start + "-" + end;
                getDiscussions();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public void getDiscussions() {
        Resources resources = getResources();
        dialog = new Dialog(getActivity());
        getDiscussionCardArray task = new getDiscussionCardArray(this);
        task.execute(range);
    }
}
