import java.net.Socket;
import java.io.*;

public class ClientExample {
    public static void main(String[] args) {
        // Tentukan alamat IP dan port server
        String serverIP = "localhost"; // Ganti dengan alamat IP sesuai server Anda
        int serverPort = 12345; // Ganti dengan port sesuai server Anda

        try {
            // Membuat socket client dan terhubung ke server di alamat IP dan port tertentu
            Socket clientSocket = new Socket(serverIP, serverPort);

            // Mendapatkan output stream untuk mengirim data ke server
            OutputStream outputStream = clientSocket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);

            // Mengirim data ke server
            writer.write("Hello, Server!");
            writer.flush();

            // Menerima data dari server
            InputStream inputStream = clientSocket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            char[] buffer = new char[1024];
            int bytesRead = reader.read(buffer);

            // Menampilkan data dari server
            System.out.println("Server Response: " + new String(buffer, 0, bytesRead));

            // Menutup socket client
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
