import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LatObjectServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);

            System.out.println("Server waiting for client...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                // Menerima objek dari client
                try {
                    Object receivedObject = objectInputStream.readObject();
                    System.out.println("Received object from client: " + receivedObject);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // Menutup socket client
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
