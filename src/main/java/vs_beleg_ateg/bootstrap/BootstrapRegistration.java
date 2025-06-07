package vs_beleg_ateg.bootstrap;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;

public class BootstrapRegistration
{
    public static void main(String args[])
    {
        try
        {
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
