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
    double xmin = -1.666, xmax = 1, ymin = -1, ymax = 1;

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

        int thread_sum = 4;
        Thread[] threads = new Thread[thread_sum];

        int x_length = imageWidth/thread_sum;
        Color[][] bild = new Color[imageWidth][imageHeight];
        
        for (int i = 1; i < stepCount; i++) { // Round loop
            for (int j = 0; j < thread_sum; j++) {
                final int x_start = x_length * j;
                final int x_stop = (j == thread_sum - 1) ? imageWidth : x_start + x_length;

                threads[j] = new Thread(() -> {
                    Color[][] bild_teil = bild_rechnen_worker(maxIterations, imageWidth, imageHeight, xmin, xmax, ymin, ymax, x_start, x_stop);

                    for (int x = x_start; x < x_stop; x++) {
                        for (int y = 0; y < imageHeight; y++) {
                            bild[x][y] = bild_teil[x][y];
                        }
                    }
                });
                threads[j].start();
            }

            for (Thread t : threads) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //zeichne das Bild
            gui.givePixelData(bild, imageWidth, imageHeight);

            double xdim = xmax - xmin;
            double ydim = ymax - ymin;
            xmin = zoomPointX - xdim / 2 / zoomFactor;
            xmax = zoomPointX + xdim / 2 / zoomFactor;
            ymin = zoomPointY - ydim / 2 / zoomFactor;
            ymax = zoomPointY + ydim / 2 / zoomFactor;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //MUSS AUF WORKER VERSCHOBEN WERDEN
    private Color[][] bild_rechnen_worker(int max_iter, int xpix, int ypix, double xmin, double xmax, double ymin, double ymax, int x_start, int x_stop) {
        //TODO: MUSS AUF WORKER x_start, x_stop IMPLEMENTIEREN!!!
        
        Color[][] colors = new Color[xpix][ypix];

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
    private int calc(int max_iter, double cr, double ci) {
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