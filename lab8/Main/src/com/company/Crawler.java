package com.company;

import java.io.*;
import java.net.Socket;

public class Crawler implements Runnable {
    urlPool urlPoolT;
    public Crawler(urlPool pool) {
        urlPoolT = pool;
    }

    public static final String URL_PREFIX = "<a href=\"http";
    public static StringBuilder resultLink = new StringBuilder();
    public void crawler()
    {
        urlDepthPair currentPair=urlPoolT.getPair();
        resultLink.append(currentPair.toString()).append(" ").append(currentPair.getDepth()).append("\n");
        try
        {
            Socket socket = new Socket(currentPair.getURL().getHost(),80);
            socket.setSoTimeout(3000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("GET " + currentPair.getURL().getPath() + " HTTP/1.1");
            out.println("Host: " + currentPair.getURL().getHost());
            out.println("Connection: close");
            out.println();
            out.flush();
            String currentLine = null;
            while ((currentLine = in.readLine()) != null)
            {
                if (currentPair.getDepth()<urlPoolT.maxDepth)
                {
                    try {
                        if (currentLine.contains(URL_PREFIX)) {
                            String newURL = currentLine.substring(currentLine.indexOf(URL_PREFIX) + 9, currentLine.indexOf("\"", currentLine.indexOf(URL_PREFIX) + 9));
                            urlDepthPair newPair = new urlDepthPair(newURL, currentPair.getDepth() + 1);
                            if (resultLink.indexOf(newURL) == -1 )
                                urlPoolT.addPair(newPair);
                        }
                    }
                    catch (IndexOutOfBoundsException ignored)
                    {
                    }
                }
            }

        }
        catch (IOException ignored)
        {
        }
    }

    @Override
    public void run() {
        while (true)
        {
            crawler();
        }
    }
}
