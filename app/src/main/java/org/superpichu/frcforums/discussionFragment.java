package org.superpichu.frcforums;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class discussionFragment extends ListFragment {
    private ArrayList<Discussion> discussions;
    public String range;
    public discussionAdapter adapter;
    public Dialog dialog;
    OnThreadSelectedListener listener;

    public interface OnThreadSelectedListener{
        public void OnThreadSelected(String id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnThreadSelectedListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, null);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            range="1-20";
            getDiscussions();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDivider(new ColorDrawable(64000000));
        getListView().setDividerHeight(1);
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        Discussion item = (Discussion)getListAdapter().getItem(position);
        String title = item.name;
        String dId = String.valueOf(item.id);
        if(title.contains("<")){
            title = title.split("<")[0];
        }
        getActivity().setTitle(title);
        listener.OnThreadSelected(dId);
        System.out.println(dId);
        //Toast.makeText(getActivity(), item.name, Toast.LENGTH_SHORT).show();
    }


    public View.OnClickListener nextAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Discussion item = (Discussion)getListAdapter().getItem(0);
                int max = item.max;
                int start = Integer.parseInt(range.split("-")[1]);
                if(start == max){
                    start = max - 20;
                }
                int end = start+20;
                if(end > max) {
                    end = max;
                }
                start++;
                range = start+"-"+end;
                getDiscussions();

            }catch (Exception e){
            }
            System.out.println("Discussion");
        }
    };

    public View.OnClickListener prevAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                int end = Integer.parseInt(range.split("-")[0]);
                if(end <= 1){
                    end = 21;
                }
                int start = end - 20;
                if(start < 1){
                    start = 1;
                }
                end--;
                range = start+"-"+end;
                getDiscussions();

            }catch (Exception e){
            }
            System.out.println("Discussion");

        }
    };

    public View.OnClickListener firstAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                range = "1-21";
                getDiscussions();
            }catch (Exception e){
            }
            System.out.println("Discussion");

        }
    };

    public View.OnClickListener lastAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Discussion item = (Discussion)getListAdapter().getItem(0);
                int end = item.max;
                int start = end - 20;
                range = start+"-"+end;
                getDiscussions();
            }catch (Exception e){
            }
            System.out.println("Discussion");

        }
    };
    public void getDiscussions(){
        Resources resources = getResources();
        dialog = new Dialog(getActivity());
        getDiscussionArray task = new getDiscussionArray(this);
        task.execute(range);
    }
}
