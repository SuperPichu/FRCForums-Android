package org.superpichu.frcforums;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by chris on 4/21/15.
 */
public class Global {
    public static boolean login = false;
    public static String cookies;
    public static String TransientKey;
    public static DefaultHttpClient defaultClient = new DefaultHttpClient();
}
