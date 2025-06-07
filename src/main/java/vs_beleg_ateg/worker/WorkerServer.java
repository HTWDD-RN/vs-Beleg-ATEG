package vs_beleg_ateg.worker;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import vs_beleg_ateg.bootstrap.Bootstrap;

public class WorkerServer {
    public static void main(String[] args) {
        String workerId = String.valueOf((int)(Math.random() * 10000));
        int port = 1099;
        String addr = args[0];
        try {
            String ip;
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
            }

            // Vor dem Stub-Export setzen!
            System.setProperty("java.rmi.server.hostname", ip);
            System.out.println("Worker RMI hostname gesetzt auf: " + ip);

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
