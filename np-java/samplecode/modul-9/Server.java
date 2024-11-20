import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Buat instance objek remote
            HelloImpl obj = new HelloImpl();

            // Buat registry pada port default 1099
            Registry registry = LocateRegistry.createRegistry(2000);

            // Bind objek ke registry dengan nama "HelloService"
            registry.rebind("HelloService", obj);

            System.out.println("Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
