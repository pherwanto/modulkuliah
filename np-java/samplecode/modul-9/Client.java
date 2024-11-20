import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            // Hubungkan ke registry pada localhost
            Registry registry = LocateRegistry.getRegistry("localhost",2000);

            // Cari objek remote dengan nama "HelloService"
            Hello stub = (Hello) registry.lookup("HelloService");

            // Panggil metode remote
            String response = stub.sayHello("World");
            System.out.println("Response from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
