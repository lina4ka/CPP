package com.company;

import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator {
    public static final int MAX_ITERATIONS = 20000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2;
        range.height = 4;
        range.width = 4;
    }

    public int numIterations(double x, double y) {
        double cX = x;
        double cY = y;
        int i = 0;
        do {
            i++;
            double temp = x * x - y * y +cX;
            y = -2 * x * y +cY;
            x=temp;
        }while ((i < MAX_ITERATIONS&&(x * x + y * y) < 4));
        return i == MAX_ITERATIONS ?  -1 : i;
    }
    public String toString()
    {
        return "Tricorn";
    }

}