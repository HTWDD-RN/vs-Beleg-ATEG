package vs_beleg_ateg.worker;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

import vs_beleg_ateg.mandelbrotengine.MandelbrotCalculator;
import vs_beleg_ateg.worker.Task;

public class WorkerImpl extends UnicastRemoteObject
    implements WorkerInterface {
    Task task;

    public WorkerImpl() throws RemoteException{
        //this.task = newTask;
        super();
    }
    
    public TaskResult computeTask(Task task) throws RemoteException{
        System.out.println("working");
        WorkerThread worker = new WorkerThread(task);
        worker.start();

        try {
            worker.join(); // Warten bis Thread fertig ist
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RemoteException("Thread interrupted", e);
        }

        return worker.getResult();
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
