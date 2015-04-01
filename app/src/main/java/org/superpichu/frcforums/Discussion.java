package org.superpichu.frcforums;

import android.graphics.Bitmap;

/**
 * Created by chris on 4/1/15.
 */
public class Discussion {
    public String name;
    public String description;
    public int id;
    public Bitmap icon;

    public Discussion(String name, String description,int id,Bitmap icon){
        this.name = name;
        this.description = description;
        this.id = id;
        this.icon = icon;
    }
}
