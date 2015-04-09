package org.superpichu.frcforums;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by chris on 4/9/15.
 */
@Root
public class Credentials {
    public Credentials(){
    }

    @Element
    private String user;

    public String getUser(){
        return user;
    }

    @Element
    private String pass;

    public String getPass(){
        return pass;
    }
    public Credentials(String user,String pass){
        this.user = user;
        this.pass = pass;
    }
}
