package vs_beleg_ateg.worker;
package vs_beleg_ateg.controller;


import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.awt.image.BufferedImage;

public interface WorkerInterface extends Remote {
    TaskResult computeTask(Task task) throws RemoteException;
}

public interface ControllerInterface {
    BufferedImage startCalculation();
}