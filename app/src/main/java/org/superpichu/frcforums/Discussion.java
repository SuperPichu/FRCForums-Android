package org.superpichu.frcforums;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by chris on 4/1/15.
 */
public class Discussion {
    public String name;
    public String description;
    public int id;
    public Bitmap icon;
    public ArrayList<Comment> comments;
    public int max;
    public boolean read = true;
    public int unreadCount;
    public int commentMax;

    public Discussion(){
    }
}
