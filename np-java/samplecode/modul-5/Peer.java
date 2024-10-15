import java.io.*;
import java.net.*;

public class Peer {
    public static void main(String[] args) {
        try {
            // Buat server socket untuk menerima koneksi
            ServerSocket serverSocket = new ServerSocket(12345);

            System.out.println("Peer Menunggu Koneksi...");

            // Terima koneksi dari peer lain
            Socket clientSocket = serverSocket.accept();

            // Buat input stream untuk menerima data
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Baca data dari peer lain
            String receivedData = in.readLine();
            System.out.println("Data diterima: " + receivedData);

            // Buat output stream untuk mengirim data
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Kirim data ke peer lain
            out.println("Hello from Peer!");

            // Tutup soket dan stream
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}