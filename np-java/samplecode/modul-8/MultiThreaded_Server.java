import java.io.*;
import java.net.*;

// Kelas ClientHandler: Menangani komunikasi dengan klien dalam thread terpisah
class ClientHandler implements Runnable {
    private Socket clientSocket;

    // Konstruktor menerima socket klien yang terhubung
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Kirim pesan awal ke klien
            out.println("Hello, you are connected to the server!");

            String message;
            // Terima pesan dari klien dan kirim balasan sampai klien memutuskan koneksi
            while ((message = in.readLine()) != null) {
                System.out.println("Received from client: " + message);
                out.println("Echo: " + message); // Kirim balasan
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

// Kelas Server: Menunggu koneksi dari klien dan membuat thread untuk masing-masing koneksi
public class MultiThreaded_Server {
    public static void main(String[] args) {
        int port = 5000; // Port server

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            // Loop tak terbatas untuk menerima koneksi klien
            while (true) {
                // Menunggu koneksi klien
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());

                // Buat ClientHandler baru untuk klien dan jalankan dalam thread
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
