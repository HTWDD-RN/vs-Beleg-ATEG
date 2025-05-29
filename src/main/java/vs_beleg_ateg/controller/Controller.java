package vs_beleg_ateg.controller;
import java.awt.Color;
import vs_beleg_ateg.gui.GUI;

public class Controller {
    private int imageWidth, imageHeight;
    private double zoomPointX, zoomPointY;
    private double zoomFactor;
    private int stepCount;
    private int maxIterations;
    private GUI gui;

    public Controller(int imageWidth, int imageHeight, double zoomPointX, double zoomPointY, double zoomFactor, int stepCount, int maxIterations, int workerCount, GUI gui) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.zoomPointX = zoomPointX;
        this.zoomPointY = zoomPointY;
        this.zoomFactor = zoomFactor;
        this.stepCount = stepCount;
        this.maxIterations = maxIterations;
        this.gui = gui;
    }

    //TODO
    //while round                               OK
    //ci cr, zoom berechnen                     OK
    //schick werte an der worker                OK
    //fertig                                    OK

    public void startComputation(){
        Color[][] c = new Color[imageWidth][imageHeight];
        double xmin = -1.666, xmax = 1, ymin = -1, ymax = 1;

        for (int i = 1; i < stepCount; i++) { // Round loop
            //für test
            System.out.println(xmin + "- " + xmax + "- " + ymin + "- " + ymax + "-" + zoomPointX + "-" + zoomPointY);

            //TODO
            //schicke Variables an Workers
            c = bild_rechnen_worker(maxIterations, imageWidth, imageHeight, xmin, xmax, ymin, ymax);

            //zeichne das Bild
            gui.givePixelData(c, imageWidth, imageHeight);

            double xdim = xmax - xmin;
            double ydim = ymax - ymin;
            xmin = zoomPointX - xdim / 2 / zoomFactor;
            xmax = zoomPointX + xdim / 2 / zoomFactor;
            ymin = zoomPointY - ydim / 2 / zoomFactor;
            ymax = zoomPointY + ydim / 2 / zoomFactor;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //MUSS AUF WORKER VERSCHOBEN WERDEN
    public Color[][] bild_rechnen_worker(int max_iter, int xpix, int ypix, double xmin, double xmax, double ymin, double ymax) {
        Color[][] colors = new Color[xpix][ypix]; // [y][x]

        for (int y = 0; y < ypix; y++) {
            double c_im = ymin + (ymax - ymin) * y / ypix;

            for (int x = 0; x < xpix; x++) {
                double c_re = xmin + (xmax - xmin) * x / xpix;
                int iter = calc(max_iter, c_re, c_im);

                if (iter == max_iter) {
                    colors[x][y] = Color.BLACK;
                } else {
                    float c = (float) iter / max_iter;
                    colors[x][y] = Color.getHSBColor(c, 1f, 1f);
                }
            }
        }

        return colors;
    }

    //MUSS AUF WORKER VERSCHOBEN WERDEN
    public int calc(int max_iter, double cr, double ci) {
        int iter;
        double zr = 0, zi = 0, zr2 = 0, zi2 = 0, zri = 0, betrag = 0;
        for (iter = 0; iter < max_iter && betrag <= 4.0; iter++) {
            zr = zr2 - zi2 + cr;
            zi = zri + zri + ci;

            zr2 = zr * zr;
            zi2 = zi * zi;
            zri = zr * zi;
            betrag = zr2 + zi2;
        }
        return iter;
    }
}
