import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost"; // Alamat server (localhost jika server berjalan di mesin yang sama)
        int port = 5000; // Port yang digunakan oleh server

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server.");

            // Setup untuk membaca dan menulis data dari/ke server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Membaca pesan dari server saat terhubung
            System.out.println("Server: " + in.readLine());

            // Setup untuk input dari pengguna
            Scanner scanner = new Scanner(System.in);
            String message;

            // Loop untuk mengirim pesan ke server dan menerima respons
            while (true) {
                System.out.print("Enter message to send to server (type 'exit' to quit): ");
                message = scanner.nextLine();

                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println("Exiting the connection.");
                    break;
                }

                // Kirim pesan ke server
                out.println(message);

                // Terima balasan dari server
                String response = in.readLine();
                System.out.println("Server: " + response);
            }

            // Tutup scanner dan koneksi
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
