package vs_beleg_ateg.worker;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import vs_beleg_ateg.worker.WorkerImpl;
import vs_beleg_ateg.shared.WorkerInterface;
import java.rmi.Naming;

public class WorkerServer {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java WorkerServer <master> <workerId>");
            System.exit(1);
        }
        String master = "localhost";        //args[0];
        String workerId = args[1];
        int port = 1099;
        try {
            WorkerImpl worker = new WorkerImpl();

            //System.out.println("Vor Create stub");
            //WorkerInterface stub = (WorkerInterface) UnicastRemoteObject.exportObject(worker, 0);
            
            System.out.println("Vor getRegistry");
            Registry registry = LocateRegistry.getRegistry(master);
            
            System.out.println("Vor Rebind");
            Naming.rebind("rmi://"+master+"/worker" + workerId, worker);
            
            System.out.println("Worker ready.");
        } catch (Exception e) {
            System.out.println(e);
            //e.print(e);
        }
    }
}
