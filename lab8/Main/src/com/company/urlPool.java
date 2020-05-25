package com.company;

import java.util.LinkedList;

public class urlPool {
    LinkedList<urlDepthPair> findLinks;
    LinkedList<urlDepthPair> resultLinks;
    int maxDepth;
    int wait;

    public urlPool(int maxDepth)
    {
        this.maxDepth = maxDepth;
        findLinks = new LinkedList<urlDepthPair>();
        resultLinks = new LinkedList<urlDepthPair>();
        wait = 0;
    }

    public synchronized urlDepthPair getPair()
    {
        while (findLinks.size() == 0) {
            wait++;
            try
            {
                wait();
            }
            catch (InterruptedException ignored)
            {
            }
            wait--;
        }
        urlDepthPair nextPair = findLinks.getFirst();
        findLinks.removeFirst();
        return nextPair;
    }
    public synchronized void addPair(urlDepthPair pair)
    {
        if (!resultLinks.contains(pair))
        {
            resultLinks.add(pair);
            if (pair.getDepth() < maxDepth)
            {
                findLinks.add(pair);
                notify();
            }
        }
    }
    public synchronized int getWait()
    {
        return wait;
    }
    public String getResult()
    {
        StringBuilder result = new StringBuilder();
        for (urlDepthPair c : resultLinks)
        {
            try {
                result.append("Глубина = ").append(c.getDepth()).append(" ссылка = ").append(c.toString()).append("\n");
            }
            catch (NullPointerException ignored)
            {
            }
        }
        return result.toString();
    }
}

