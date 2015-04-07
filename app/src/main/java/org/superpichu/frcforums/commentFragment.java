package org.superpichu.frcforums;

import android.app.Dialog;
import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by chris on 4/2/15.
 */
public class commentFragment extends ListFragment {
    private ArrayList<Comment> comments;
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
            range="1-20";
            id="902";
            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.loading);
            getComments(range,id);
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
        nextC.setOnClickListener(nextActionC);
        prevC.setOnClickListener(prevActionC);
        firstC.setOnClickListener(firstActionC);
        lastC.setOnClickListener(lastActionC);
        //TextView body = (TextView)getListView().
        //body.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public View.OnClickListener nextActionC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Comment item = (Comment)getListAdapter().getItem(0);
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
                System.out.println(id);
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
                range = "1-21";
                getComments(range, id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    public View.OnClickListener lastActionC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
                Comment item = (Comment)getListAdapter().getItem(0);
                int end = item.max;
                int start = end - 20;
                range = start+"-"+end;
                getComments(range, id);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    };

    public void getComments(String range, String dId){
        //Activity activity1 = getActivity();
        Resources resources = getResources();
        this.id = dId;
        getCommentArray task = new getCommentArray(this);
        String[] data = {dId, range};
        task.execute(data);
    }
}