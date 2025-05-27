package vs_beleg_ateg.worker;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WorkerInterface extends Remote {
    TaskResult computeTask(Task task) throws RemoteException;
}