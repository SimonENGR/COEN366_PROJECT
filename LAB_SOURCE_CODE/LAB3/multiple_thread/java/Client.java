import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverHost = "127.0.0.1"; // Server IP
        int port = 65432; // Server Port

        try (Socket socket = new Socket(serverHost, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server at " + serverHost + ":" + port);

            // Start a thread for reading messages from the server
            Thread receiveThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = input.readLine()) != null) {
                        System.out.println("Server: " + response);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });
            receiveThread.start();

            // Read user input and send messages to the server
            while (true) {
                System.out.print("You: ");
                String message = scanner.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    socket.close();
                    break;
                }
                output.println(message);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
