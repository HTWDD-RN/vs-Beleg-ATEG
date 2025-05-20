package vs_beleg_ateg.controller;

import vs_beleg_ateg.worker.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
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
            Registry registry = LocateRegistry.getRegistry("localhost", 1199);
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
        return startComputation();
    }

    private BufferedImage startComputation() {
        BufferedImage resultImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        List<Task> tasks = divideTasks();
        List<TaskResult> results = collectResults(tasks);

        // Setze die berechneten Pixel ins Bild ein
        for (int i = 0; i < results.size(); i++) {
            Task task = tasks.get(i);
            TaskResult result = results.get(i);
            for (int x = 0; x < result.getWidth(); x++) {
                for (int y = 0; y < result.getHeight(); y++) {
                    int iter = result.getPixelData()[x][y];
                    int color = iterToColor(iter, maxIterations);
                    resultImage.setRGB(x, task.getY() + y, color);
                }
            }
        }

        return resultImage;
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

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return resultBuffer;
    }

    private int iterToColor(int iter, int max) {
        if (iter == max) return 0x000000;
        float hue = iter / (float) max;
        return Color.HSBtoRGB(hue, 1f, 1f);
    }
}
