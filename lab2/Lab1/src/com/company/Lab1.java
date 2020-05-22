package com.company;

public class Lab1 {
    public static double computeArea(Point3d f,Point3d s,Point3d t)
    {
        double a = Math.sqrt(Math.pow(f.getX() - s.getX(), 2) + Math.pow(f.getY() - s.getY(), 2));
        double b = Math.sqrt(Math.pow(f.getX() - t.getX(), 2) + Math.pow(f.getY() - t.getY(), 2));
        double c = Math.sqrt(Math.pow(s.getX() - t.getX(), 2) + Math.pow(s.getY() - t.getY(), 2));
        double p = (a + c + b) / 2;
        return Math.sqrt(p * (p - a) * (p - c) * (p - b));
    }
    public static void main(String[] args)
    {
        Point3d firstPoint = new Point3d();
        Point3d secondPoint = new Point3d(3.0,3.3,-1);
        Point3d thirdPoint = new Point3d(-2,5,1);
        if (!firstPoint.equals(secondPoint)&&!firstPoint.equals(thirdPoint)&&!secondPoint.equals(thirdPoint))
            System.out.println(computeArea(firstPoint,secondPoint,thirdPoint));
        else
            System.out.println("одинаковые точки");
    }
}