package vs_beleg_ateg.worker;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WorkerInterface extends Remote {
    /*void registerWorker(Worker worker)throws RemoteException;
    int getWorkerCount() throws RemoteException;*/
    int[][] computeTask(double startX,double startY,double endX,double endY, int width,int height,int iteration) throws RemoteException;
}