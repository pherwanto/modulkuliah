import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiPeerServer {
    private static final int PORT = 12345;
    private static List<PeerHandler> peers = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New peer connected: " + clientSocket.getInetAddress());

                // Membuat handler baru untuk peer dan menambahkannya ke daftar
                PeerHandler peerHandler = new PeerHandler(clientSocket);
                peers.add(peerHandler);

                // Memulai thread untuk menangani koneksi dengan peer
                new Thread(peerHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast pesan ke semua peer
    public static void broadcastMessage(String message, PeerHandler sender) {
        synchronized (peers) {
            for (PeerHandler peer : peers) {
                if (peer != sender) {
                    peer.sendMessage(message);
                }
            }
        }
    }
}

class PeerHandler implements Runnable {
    private Socket peerSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public PeerHandler(Socket peerSocket) {
        try {
            this.peerSocket = peerSocket;
            this.reader = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
            this.writer = new PrintWriter(peerSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String username = null;
        try {
            username = reader.readLine();
            System.out.println("New peer connected: " + username);

            // Broadcast pesan bahwa peer baru telah bergabung
            MultiPeerServer.broadcastMessage(username + " has joined the chat.", this);

            String peerMessage;
            while ((peerMessage = reader.readLine()) != null) {
                // Broadcast pesan ke semua peer
                MultiPeerServer.broadcastMessage(username + ": " + peerMessage, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Handle jika peer terputus
            try {
                reader.close();
                writer.close();
                peerSocket.close();

                // Hapus handler dari daftar
                MultiPeerServer.peers.remove(this);

                // Broadcast pesan bahwa peer telah keluar
                MultiPeerServer.broadcastMessage(username + " has left the chat.", this);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Mengirim pesan ke peer
    public void sendMessage(String message) {
        writer.println(message);
    }
}
