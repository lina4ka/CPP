package com.company;


public class Main {
    public static void main(String[] args)
    {
        boolean firstDigit = true;
        boolean secondDigit = true;
        for (int i = 0 ; i<args[1].length();i++)
            firstDigit = Character.isDigit(args[1].charAt(i)) ? firstDigit : false;
        for (int i = 0 ; i<args[2].length();i++)
            secondDigit = Character.isDigit(args[2].charAt(i)) ? secondDigit : false;
        if (args.length == 3&&firstDigit&&secondDigit)
        {
            String lineUrl = args[0];
            int numThreads = Integer.parseInt(args[2]);
            urlPool pool = new urlPool(Integer.parseInt(args[1]));
            pool.addPair(new urlDepthPair(lineUrl, 0));
            for (int i = 0; i < numThreads; i++) {
                Crawler c = new Crawler(pool);
                Thread t = new Thread(c);
                t.start();
            }
            while (pool.getWait() != numThreads) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
            System.out.print(pool.getResult());
            System.exit(0);
        }
        else
        {
            if (args.length!=3)
                System.out.println("usage: java Crawler <URL> <maximum_depth> <num_threads>");
            else if (!firstDigit)
                System.out.println("Второй параметр не является числом");
            else
                System.out.println("Третий параметр не является числом");
        }
    }
}

