package org.superpichu.frcforums;

import android.app.Dialog;
import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by chris on 4/2/15.
 */
public class commentCardFragment extends Fragment {
    public ArrayList<Comment> comments;
    public String range;
    public commentAdapter adapter;
    public Dialog dialog;
    public String id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_discussion_view, container, false);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            range="#latest";
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.loading);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button nextC = (Button)view.findViewById(R.id.nextC);
        Button prevC = (Button)view.findViewById(R.id.prevC);
        Button firstC = (Button)view.findViewById(R.id.firstC);
        Button lastC = (Button)view.findViewById(R.id.lastC);
        Button post = (Button)view.findViewById(R.id.post);
        nextC.setOnClickListener(nextActionC);
        prevC.setOnClickListener(prevActionC);
        firstC.setOnClickListener(firstActionC);
        lastC.setOnClickListener(lastActionC);
        post.setOnClickListener(postComment);
    }

    public View.OnClickListener nextActionC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Comment item = comments.get(0);
                int page = item.page;
                page++;
                range = "/p"+page;
                getComments(range, id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    public View.OnClickListener prevActionC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Comment item = comments.get(0);
                int page = item.page;
                page--;
                range = "/p"+page;
                getComments(range, id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    public View.OnClickListener firstActionC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                getComments("/p1", id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    public View.OnClickListener lastActionC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                getComments("#latest", id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    public void getComments(String range, String dId){
        Resources resources = getResources();
        this.id = dId;
        getCommentArray task = new getCommentArray(this);
        String[] data = {dId, range};
        task.execute(data);
    }

    public View.OnClickListener postComment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Intent intent = new Intent(getActivity(), writePost.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };
}