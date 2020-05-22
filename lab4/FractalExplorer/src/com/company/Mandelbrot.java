package com.company;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.height = 3;
        range.width = 3;
    }

    public int numIterations(double x, double y) {
        double cX = x;
        double cY = y;
        int i = 0;
        do {
            i++;
            double temp = x * x - y * y +cX;
            y = 2 * x * y +cY;
            x=temp;
        }while ((i < MAX_ITERATIONS&&(x * x + y * y) < 4));
        return i == MAX_ITERATIONS ?  -1 : i;
    }

}

