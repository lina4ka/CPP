package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int X;
    private JImageDisplay jImgDisp;
    private FractalGenerator fGenerator;
    private Rectangle2D.Double range;

    public FractalExplorer(int x) {
        X = x;
        fGenerator = new Mandelbrot();
        range = new Rectangle2D.Double(-2,-1.5,3,3);
    }
    public void createAndShowGUI()
    {
        JFrame jFrame = new JFrame("Фрактал");
        jImgDisp = new JImageDisplay(X,X);
        JButton resetB = new JButton("Сброс");
        jFrame.setLayout(new java.awt.BorderLayout());
        jFrame.add(jImgDisp, java.awt.BorderLayout.CENTER);
        jFrame.add(resetB, java.awt.BorderLayout.SOUTH);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resetB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fGenerator.getInitialRange(range);
                drawFractal();
            }
        });
        jImgDisp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double xCoord = FractalGenerator.getCoord(range.x,range.x + range.width, X, e.getX());
                double yCoord = FractalGenerator.getCoord(range.y,range.y + range.width, X, e.getY());
                fGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
                drawFractal();

            }
        });
        jFrame.pack ();
        jFrame.setVisible (true);
        jFrame.setResizable (false);
    }
    private void drawFractal()
    {
        for (int x = 0; x < X; x++) {
            for (int y = 0; y < X; y++) {
                double xCoord = FractalGenerator.getCoord (range.x, range.x + range.width,X, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, X, y);
                int i = fGenerator.numIterations(xCoord,yCoord);
                double hue = 0.7f + (float) i / 200f;
                int rgbColor = Color.HSBtoRGB((float) hue, 0.8f, 1f);
                jImgDisp.drawPixel(x, y, i ==-1? 0 : rgbColor);
            }
        }
        jImgDisp.repaint();

    }
    public static void main(String[] args) {
        FractalExplorer fracExp = new FractalExplorer(800);
        fracExp.createAndShowGUI();
        fracExp.drawFractal();
    }
}
