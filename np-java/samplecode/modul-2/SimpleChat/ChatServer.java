import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static final int PORT = 8888;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Membuat handler baru untuk client dan menambahkannya ke daftar
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                // Memulai thread untuk menangani koneksi dengan client
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast pesan ke semua client
    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    // Hapus client dari daftar
    public static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
