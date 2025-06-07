package vs_beleg_ateg.worker;

import java.rmi.registry.LocateRegistry;
import vs_beleg_ateg.bootstrap.Bootstrap;

public class WorkerServer {
    public static void main(String[] args) {
        String workerId = String.valueOf((int)(Math.random() * 1000));
        int port = 1099;
        String addr = args[0];
        try {
            WorkerInterface stub = new WorkerImpl();
            
            Bootstrap bootstrap = (Bootstrap) LocateRegistry.getRegistry(addr, port).lookup("Bootstrap");
            bootstrap.registerWorker(workerId, stub);
            bootstrap.getWorkerList();
            
            System.out.println("Worker ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
