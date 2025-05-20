package vs_beleg_ateg.controller;

import vs_beleg_ateg.worker.*;
import vs_beleg_ateg.gui.GUI;

import java.awt.image.BufferedImage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<WorkerInterface> workerList = new ArrayList<>();
    private int imageWidth, imageHeight;
    private double zoomPointX, zoomPointY;
    private double zoomFactor;
    private int stepCount;
    private int maxIterations;

    public Controller(
            int imageWidth,
            int imageHeight,
            double zoomPointX,
            double zoomPointY,
            double zoomFactor,
            int stepCount,
            int maxIterations,
            int workerCount
    ) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.zoomPointX = zoomPointX;
        this.zoomPointY = zoomPointY;
        this.zoomFactor = zoomFactor;
        this.stepCount = stepCount;
        this.maxIterations = maxIterations;

        connectToWorkers(workerCount);
    }

    private void connectToWorkers(int count) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1199); // oder IP der Master-Maschine
            for (int i = 1; i <= count; i++) {
                WorkerInterface stub = (WorkerInterface) registry.lookup("Worker" + i);
                workerList.add(stub);
            }
            System.out.println("Alle Worker verbunden: " + workerList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage startCalculation() {
        BufferedImage resultImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        int blockHeight = imageHeight / workerList.size(); // einfach in horizontale Streifen teilen

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < workerList.size(); i++) {
            int yStart = i * blockHeight;
            int yEnd = (i == workerList.size() - 1) ? imageHeight : yStart + blockHeight;
            int blockH = yEnd - yStart;

            final int workerIndex = i;
            Thread t = new Thread(() -> {
                try {
                    Task task = new Task(0, yStart, imageWidth, blockH);
                    task.setIteration(maxIterations);

                    TaskResult result = workerList.get(workerIndex).computeTask(task);

                    // Ergebnis ins finale Bild schreiben
                    for (int x = 0; x < result.getWidth(); x++) {
                        for (int y = 0; y < result.getHeight(); y++) {
                            int iter = result.getPixelData()[x][y];
                            int color = iterToColor(iter, maxIterations);
                            resultImage.setRGB(x, yStart + y, color);
                        }
                    }

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

        return resultImage;
    }

    private int iterToColor(int iter, int max) {
        if (iter == max) return 0x000000; // Schwarz (nicht entkommen)
        float hue = iter / (float) max;
        return Color.HSBtoRGB(hue, 1f, 1f);
    }
}
