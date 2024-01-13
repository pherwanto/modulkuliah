import java.io.*;
import java.net.Socket;

public class ObjectClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 8888);

            // Mengirim objek ke server
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            MyObject myObject = new MyObject("Hello from Client!");
            objectOutputStream.writeObject(myObject);
            objectOutputStream.flush();

            // Menutup socket client
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyObject implements Serializable {
    private String message;

    public MyObject(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "message='" + message + '\'' +
                '}';
    }
}
