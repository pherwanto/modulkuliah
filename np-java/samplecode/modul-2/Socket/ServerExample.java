import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class ServerExample {
    public static void main(String[] args) {
        try {
            // Membuat server socket dan mendengarkan koneksi dari client di port tertentu
            ServerSocket serverSocket = new ServerSocket(12345);
            
            while (true) {
                // Menerima permintaan koneksi dari client
                Socket clientSocket = serverSocket.accept();

                // Membuat thread untuk menangani koneksi dengan client
                Thread clientThread = new ClientHandlerThread(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandlerThread extends Thread {
    private Socket clientSocket;

    public ClientHandlerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            // Menerima data dari client
            InputStream inputStream = clientSocket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            char[] buffer = new char[1024];
            int bytesRead = reader.read(buffer);

            // Menampilkan data dari client
            System.out.println("Received from Client: " + new String(buffer, 0, bytesRead));

            // Mengirim data ke client
            OutputStream outputStream = clientSocket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write("Hello, Client!");
            writer.flush();

            // Menutup socket client
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
