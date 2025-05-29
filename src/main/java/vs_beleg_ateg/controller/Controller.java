/*
package vs_beleg_ateg.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import vs_beleg_ateg.gui.*;
import vs_beleg_ateg.worker.*;

public class Controller implements ControllerInterface{

    private List<WorkerInterface> workerList = new ArrayList<>();
    private int imageWidth, imageHeight;
    private double zoomPointX, zoomPointY;
    private double zoomFactor;
    private int stepCount;
    private int maxIterations;
    private guiInterface gui;

    public Controller(
            int imageWidth,
            int imageHeight,
            double zoomPointX,
            double zoomPointY,
            double zoomFactor,
            int stepCount,
            int maxIterations,
            int workerCount,
            guiInterface gui
    ) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.zoomPointX = zoomPointX;
        this.zoomPointY = zoomPointY;
        this.zoomFactor = zoomFactor;
        this.stepCount = stepCount;
        this.maxIterations = maxIterations;
        this.gui = gui;

        connectToWorkers(workerCount);
    }

    private void connectToWorkers(int count) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1199);
            for (int i = 1; i <= count; i++) {
                WorkerInterface stub = (WorkerInterface) registry.lookup("Worker" + i);
                workerList.add(stub);
            }
            System.out.println("Alle Worker verbunden: " + workerList.size());
        } catch (Exception e) {
            System.err.println("Fehler beim Verbinden mit Workern:");
            e.printStackTrace();
        }
    }

    public BufferedImage  startComputation() {
        BufferedImage resultImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        List<Task> tasks = divideTasks();
        List<TaskResult> results = collectResults(tasks);

        // Setze die berechneten Pixel ins Bild ein
        for (int i = 0; i < results.size(); i++) {
            Task task = tasks.get(i);
            TaskResult result = results.get(i);
            for (int x = 0; x < result.getWidth(); x++) {
                for (int y = 0; y < result.getHeight(); y++) {
                    int color = result.getPixelData()[x][y];
                    resultImage.setRGB(task.getStartX() + x, task.getStartY() + y, color);
                }
            }
        }

        gui.givePixelData(resultImage);
        return true;
    }

    private List<Task> divideTasks() {
        List<Task> taskList = new ArrayList<>();
        int blockHeight = imageHeight / workerList.size();

        for (int i = 0; i < workerList.size(); i++) {
            int yStart = i * blockHeight;
            int yEnd = (i == workerList.size() - 1) ? imageHeight : yStart + blockHeight;
            int blockH = yEnd - yStart;

            Task task = new Task(0, yStart, imageWidth, blockH);
            task.setIteration(maxIterations);
            taskList.add(task);
        }

        return taskList;
    }

    private List<TaskResult> collectResults(List<Task> taskList) {
        List<TaskResult> results = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        List<TaskResult> resultBuffer = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) resultBuffer.add(null); // Platzhalter

        for (int i = 0; i < taskList.size(); i++) {
            final int index = i;
            Thread t = new Thread(() -> {
                try {
                    TaskResult result = workerList.get(index).computeTask(taskList.get(index));
                    resultBuffer.set(index, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            threads.add(t);
            t.start();
        }
        // Warten, bis alle fertig sind
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return resultBuffer;
    }
}
*/

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
