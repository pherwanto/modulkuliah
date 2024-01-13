import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingSocketServer {
    private static final int PORT = 8888;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                // Menunggu koneksi dari client (blocking)
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Menerima pesan dari client (blocking)
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientMessage = reader.readLine();
                System.out.println("Received from client: " + clientMessage);

                // Mengirim pesan balasan ke client (blocking)
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                writer.println("Hello from Server!");

                // Menutup socket client
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
