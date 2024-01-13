import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BlockingSocketClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            // Membuat koneksi ke server (blocking)
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server");

            // Mengirim pesan ke server (blocking)
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello from Client!");

            // Menerima pesan balasan dari server (blocking)
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverMessage = reader.readLine();
            System.out.println("Received from server: " + serverMessage);

            // Menutup socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
