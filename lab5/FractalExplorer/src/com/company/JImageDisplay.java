package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class JImageDisplay extends JComponent {
    private BufferedImage buffImage;

    public JImageDisplay(int x,int y)
    {
        buffImage = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
        Dimension dimension = new Dimension(x, y);
        super.setPreferredSize(dimension);
    }
    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage (buffImage, 0, 0, buffImage.getWidth(), buffImage.getHeight(), null);
    }

    public void drawPixel(int x,int y,int rgbColor)
    {
        buffImage.setRGB(x,y,rgbColor);
    }

    public RenderedImage getBufferedImage() {
        return buffImage;
    }
}
