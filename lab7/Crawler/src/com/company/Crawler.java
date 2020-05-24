package com.company;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Crawler {
    public static final String URL_PREFIX = "<a href=\"http";
    public static LinkedList<urlDepthPair> tempLink = new LinkedList<urlDepthPair>();
    public static StringBuilder resultLink = new StringBuilder();
    public static void crawler(String str,int maxDepth)
    {
        urlDepthPair currentPair = new urlDepthPair(str,0);
        if (!currentPair.toString().isEmpty())
        {
            tempLink.add(currentPair);
            while (!tempLink.isEmpty())
            {
                currentPair=tempLink.getFirst();
                tempLink.removeFirst();
                resultLink.append("Глубина = ").append(currentPair.getDepth()).append(" ссылка = ").append(currentPair.toString()).append("\n");
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
                        if (currentPair.getDepth()<maxDepth)
                        {
                            try {
                                if (currentLine.contains(URL_PREFIX)) {
                                    String newURL = currentLine.substring(currentLine.indexOf(URL_PREFIX) + 9, currentLine.indexOf("\"", currentLine.indexOf(URL_PREFIX) + 9));
                                    urlDepthPair newPair = new urlDepthPair(newURL, currentPair.getDepth() + 1);
                                    if (resultLink.indexOf(newURL) == -1 && !tempLink.contains(newPair))
                                        tempLink.add(newPair);
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
        }
        System.out.print(resultLink);
    }
    public static void main(String[] args)
    {
        if (args.length>2)
            System.out.println("usage: java Crawler <URL><depth>");
        else
        {
            boolean isDigit = true;
            for (int i = 0; i< args[1].length()&&isDigit;i++)
                isDigit = Character.isDigit(args[1].charAt(i));
            if (isDigit) crawler(args[0], Integer.parseInt(args[1]));
        }
    }
}
