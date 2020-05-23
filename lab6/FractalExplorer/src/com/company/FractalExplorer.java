package com.company;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class FractalExplorer {
    private int X;
    private JImageDisplay jImgDisp;
    private FractalGenerator fGenerator;
    private Rectangle2D.Double range;
    JComboBox<FractalGenerator> jBox;
    private int rowsRemaining;
    private JButton resetB;
    private JButton saveB;

    public void enableUI(boolean var)
    {
        saveB.setEnabled(var);
        resetB.setEnabled(var);
        jBox.setEnabled(var);
    }
    public FractalExplorer(int x) {
        X = x;
        fGenerator = new Mandelbrot();
        range = new Rectangle2D.Double(-2,-1.5,3,3);
    }
    public void createAndShowGUI()
    {
        JFrame jFrame = new JFrame("Фрактал");
        jImgDisp = new JImageDisplay(X,X);
        resetB = new JButton("Сброс");
        saveB = new JButton("Сохранить");
        jFrame.setLayout(new BorderLayout());
        jFrame.add(jImgDisp, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel upPanel = new JPanel();
        jBox = new JComboBox<FractalGenerator> ();
        jBox.addItem(new Mandelbrot());
        jBox.addItem(new Tricorn());
        jBox.addItem(new BurningShip());
        JLabel jLabel = new JLabel("Тип фрактала");
        upPanel.add(jLabel, BorderLayout.WEST);
        upPanel.add(jBox, BorderLayout.EAST);
        jFrame.add(upPanel,BorderLayout.NORTH);
        JPanel dPanel = new JPanel();
        dPanel.add(resetB, BorderLayout.WEST);
        dPanel.add(saveB, BorderLayout.EAST);
        jFrame.add(dPanel,BorderLayout.SOUTH);
        jBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fGenerator = (FractalGenerator) jBox.getSelectedItem();
                fGenerator.getInitialRange(range);
                drawFractal();

            }
        });
        resetB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fGenerator.getInitialRange(range);
                drawFractal();
            }
        });
        saveB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                int t = chooser.showSaveDialog(jImgDisp);
                if (t == JFileChooser.APPROVE_OPTION) {
                    try {
                        javax.imageio.ImageIO.write(jImgDisp.getBufferedImage(), "png", chooser.getSelectedFile());
                    } catch (IOException e1) {
                        javax.swing.JOptionPane.showMessageDialog(jImgDisp, e1.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
        enableUI(false);
        rowsRemaining=X;
        for (int i = 0; i < X; i++)
        {
            FractalWorker rowDrawer = new FractalWorker(i);
            rowDrawer.execute();
        }

    }
    public static void main(String[] args) {
        FractalExplorer fracExp = new FractalExplorer(800);
        fracExp.createAndShowGUI();
        fracExp.drawFractal();
    }
    private class FractalWorker extends SwingWorker<Object,Object>{
        private int y;
        private int[] rgbArray;
        public FractalWorker(int y)
        {
            this.y = y;
        }
        @Override
        protected Object doInBackground() throws Exception {
            rgbArray = new int[X];
            for (int j = 0; j < X; j++) {
                double xCoord = FractalGenerator.getCoord (range.x, range.x + range.width,X, j);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.width, X, y);
                int i = fGenerator.numIterations(xCoord,yCoord);
                double hue = 0.7f + (float) i / 200f;
                int rgbColor = Color.HSBtoRGB((float) hue, 0.8f, 1f);
                rgbArray[j] =  i ==-1? 0 : rgbColor;
            }
            return null;
        }
        public void done()
        {
            for (int i = 0; i < X; i++)
                jImgDisp.drawPixel(i,y,rgbArray[i]);
            jImgDisp.repaint(0, 0, y, X, 1);
            rowsRemaining--;
            if (rowsRemaining==0)
                enableUI(true);
        }
    }
}
