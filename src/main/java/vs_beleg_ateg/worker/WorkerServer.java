package vs_beleg_ateg.worker;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import vs_beleg_ateg.worker.WorkerImpl;

public class WorkerServer {
    public static void main(String[] args) {
        try {
            //WorkerImpl worker = new WorkerImpl(4); // 4 Threads im Pool
            Registry registry = LocateRegistry.getRegistry("mandelbrot-master", 1199);
            WorkerInterface stub = (WorkerInterface) registry.lookup("Worker1")
            registry.rebind("Worker_" + System.currentTimeMillis(), worker);
            System.out.println("Worker ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
