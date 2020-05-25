package com.company;

import java.net.MalformedURLException;
import java.net.URL;

public class urlDepthPair {
    private URL url;
    private int depth;
    public urlDepthPair(String stringURL,int depth)
    {
        this.depth = depth;
        try {
            url =new URL(stringURL);
        } catch (MalformedURLException ignored) {
            url = null;
        }
    }
    public int getDepth()
    {
        return this.depth;
    }
    public URL getURL()
    {
        return url;
    }
    public String toString()
    {
        return url.toString();
    }

}
