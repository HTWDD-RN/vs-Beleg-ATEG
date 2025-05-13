
package Worker;
import MandelbrotEngine.MandelbrotCalculator;

public class Worker implements Runnable {
    private final TaskQueue taskQueue;  // Eine Warteschlange von Tasks
    public Worker(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        Task task = getTask();  // Holt den nächsten Task
        if (task != null) {
            //Verarbeite den Task (Berechnung)
            computeMandelbrot(double x, double y, int Iteration);
            
        }
    }
 
    public Task getTask() {
        return taskQueue.poll();  // Holt den nächsten Task aus der Warteschlange
    }

    public void computeMandelbrot(double x, double y, int Iteration) {
        
        MandelbrotCalculator MbEngine = new MandelbrotCalculator(x,y,Iteration);
    }
}

