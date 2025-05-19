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
        int i,j = 0;
        if (task != null) {
            int iteration = task.getIteration();
            //Verarbeite den Task (Berechnung)
            for(i= task.getStartX() ;i< task.getWidth();i++)
                for(j = task.getStartY(); i< task.getHeight();i++){
                     int pixel = computeMandelbrot(i, j,iteration);
                     result.setPixel(pixel, i, j);
                }
        }
    }

    public int computeMandelbrot(double x, double y, int Iteration) {
        
        //MandelbrotCalculator MbEngine = new MandelbrotCalculator();
        int pix = MandelbrotCalculator.MandelbrotCalculator(x, y, Iteration);
        return pix;
    }

    public TaskResult getResult() {
        return result;
    }

    
}
