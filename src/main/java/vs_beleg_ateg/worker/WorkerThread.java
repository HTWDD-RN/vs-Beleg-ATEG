package vs_beleg_ateg.worker;

import vs_beleg_ateg.mandelbrotengine.MandelbrotCalculator;
import vs_beleg_ateg.worker.Task;

public class WorkerThread extends Thread {
    Task task;
    TaskResult result;
    public WorkerThread(Task ToDo){
        this.task = ToDo;
        this.result = new TaskResult(ToDo.getWidth(),ToDo.getHeight());
    }

    @Override
    public void run() {
        int width = task.getWidth();
        int height = task.getHeight();
        int iteration = task.getIteration();

        double xStart = task.getStartX();
        double yStart = task.getStartY();
        double xEnd = task.getEndX();
        double yEnd = task.getEndY();
        //px: x-Pixel; py: y-Pixel; 
        //x: x-Koordinate; y: y-Koordinate
        for (int px = 0; px < width; px++) {
            for (int py = 0; py < height; py++) {
                // Umrechnung von Pixelkoordinaten in double-Koordinaten
                double x = xStart + px * (xEnd - xStart) / (width - 1);
                double y = yStart + py * (yEnd - yStart) / (height - 1);

                int pixelValue = computeMandelbrotPixel(x, y, iteration);
                result.setPixel(pixelValue, px, py);
            }
        }
    }

    public int computeMandelbrotPixel(double x, double y, int Iteration) {
        
        int pix = MandelbrotCalculator.MandelbrotCalculator(x, y, Iteration);
        return pix;
    }

    public TaskResult getResult() {
        return result;
    }

    
}
