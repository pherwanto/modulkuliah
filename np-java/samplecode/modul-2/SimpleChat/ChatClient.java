import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);

            // Input username
            System.out.print("Enter your username: ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();

            // Mengirim username ke server
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            writer.write(username);
            writer.newLine();
            writer.flush();

            // Menerima dan menampilkan pesan dari server
            Thread messageReceiver = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String serverMessage;
                    while ((serverMessage = reader.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            messageReceiver.start();

            // Mengirim pesan ke server
            while (true) {
                String clientMessage = scanner.nextLine();
                writer.write(clientMessage);
                writer.newLine();
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
