package vs_beleg_ateg.controller;
import java.awt.Color;
import java.rmi.RemoteException;

import vs_beleg_ateg.gui.GUI;
import vs_beleg_ateg.worker.Task;
import vs_beleg_ateg.worker.TaskResult;
import vs_beleg_ateg.worker.WorkerImpl;
import vs_beleg_ateg.mandelbrotengine.MandelbrotCalculator;

public class Controller {
    private int imageWidth, imageHeight;
    private double zoomPointX, zoomPointY;
    private double zoomFactor;
    private int stepCount;
    private int maxIterations;
    private int workerCount;
    private GUI gui;
    double xmin = -1.666, xmax = 1, ymin = -1, ymax = 1;

    public Controller(int imageWidth, int imageHeight, double zoomPointX, double zoomPointY, double zoomFactor, int stepCount, int maxIterations, int workerCount, GUI gui) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.zoomPointX = zoomPointX;
        this.zoomPointY = zoomPointY;
        this.zoomFactor = zoomFactor;
        this.stepCount = stepCount;
        this.workerCount =  workerCount;
        this.maxIterations = maxIterations;
        this.gui = gui;
    }

    public void startComputation(){
        // bild in 4 vertikale Streifen teilen
        int thread_count = workerCount;
        Thread[] threads = new Thread[thread_count];
        TaskResult[] results = new TaskResult[thread_count];

        int x_length = imageWidth/thread_count;
        Color[][] bild = new Color[imageWidth][imageHeight];
        int i;
        for (i = 1; i <= stepCount; i++) { // Round loop
            for (int j = 0; j < thread_count; j++) {
                final int threadIndex = j; //worker 0-3
                final int x_startPixel = x_length * j; //Startindex in X-Richtung (Pixel), ab wo dieser Task rechnen soll. fängt von 0
                final int x_stopPixel = (j == thread_count - 1) ? imageWidth : x_startPixel + x_length; //Ende des Pixelbereichs.

                double xStart = xmin + (x_startPixel / (double) imageWidth) * (xmax - xmin);
                double xEnd = xmin + (x_stopPixel / (double) imageWidth) * (xmax - xmin);

                Task task = new Task(
                    xStart, ymin,   // reale Startkoordinaten (x, y)
                    xEnd, ymax,     // reale Endkoordinaten (x, y)
                    x_stopPixel - x_startPixel, // Breite in Pixeln
                    imageHeight,
                    maxIterations
                );

                threads[j] = new Thread(() -> {
                    try {
                        WorkerImpl worker = new WorkerImpl(task);
                        TaskResult result = worker.computeTask(task);
                        results[threadIndex] = result;
                    } catch (RemoteException e) {
                        System.err.println("Fehler bei Worker " + threadIndex + ": " + e);
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

            double xdim = xmax - xmin;
            double ydim = ymax - ymin;
            xmin = zoomPointX - xdim / 2 / zoomFactor;
            xmax = zoomPointX + xdim / 2 / zoomFactor;
            ymin = zoomPointY - ydim / 2 / zoomFactor;
            ymax = zoomPointY + ydim / 2 / zoomFactor;

            int[][] fullImage = new int[imageWidth][imageHeight];
            for (int j = 0; j < thread_count; j++) {
                int[][] part = results[j].getPixelData();
                int xOffset = j * x_length;

                for (int x = 0; x < part.length; x++) {
                    for (int y = 0; y < imageHeight; y++) {
                        fullImage[xOffset + x][y] = part[x][y];
                    }
                }
            }
            gui.givePixelData(fullImage, imageWidth, imageHeight);

            try {
                Thread.sleep(1);
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