package vs_beleg_ateg.bootstrap;
import java.rmi.registry.LocateRegistry;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.*;

public class BootstrapRegistration
{
    public static void main(String args[])
    {
        try
        {
            // Dynamisch die IP bestimmen, die für Internetverbindungen verwendet wird
            String ip;
            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
            }

            // Diese IP für RMI setzen
            System.setProperty("java.rmi.server.hostname", ip);
            System.out.println("RMI Server hostname gesetzt auf: " + ip);

            LocateRegistry.createRegistry(1099);
            Naming.rebind("Bootstrap", new BootstrapImpl());
            System.out.println("Bootstrap-Server ist gestartet");
        }
        catch(Exception e)
        {
            System.out.println("Ausnahme: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
