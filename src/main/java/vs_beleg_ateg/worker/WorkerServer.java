package vs_beleg_ateg.worker;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import vs_beleg_ateg.worker.WorkerImpl;

public class WorkerServer {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java WorkerServer <master> <workerId> <port>");
            System.exit(1);
        }
        String master = args[0];
        String workerId = args[1];
        int port = Integer.parseInt(args[2]);
        try {
            WorkerInterface worker = new WorkerImpl(); // 4 Threads im Pool

             WorkerInterface stub = (WorkerInterface) UnicastRemoteObject.exportObject(worker, 0);
            
            Registry registry = LocateRegistry.getRegistry(master, port);

            registry.rebind("Worker" + workerId, stub);
            //registry.rebind("Worker_" + System.currentTimeMillis(), worker);
            System.out.println("Worker ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
