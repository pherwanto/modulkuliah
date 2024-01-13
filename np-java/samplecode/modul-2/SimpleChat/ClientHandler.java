import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String username = reader.readLine();
            System.out.println("New user connected: " + username);

            // Broadcast pesan bahwa user baru telah bergabung
            ChatServer.broadcastMessage(username + " has joined the chat.", this);

            String clientMessage;
            while ((clientMessage = reader.readLine()) != null) {
                // Broadcast pesan ke semua client
                ChatServer.broadcastMessage(username + ": " + clientMessage, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Handle jika client terputus
            try {
                reader.close();
                writer.close();
                clientSocket.close();

                // Hapus handler dari daftar
                ChatServer.removeClient(this);

                // Broadcast pesan bahwa user telah keluar
                ChatServer.broadcastMessage("User has left the chat.", this);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Mengirim pesan ke client
    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
