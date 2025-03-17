import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 65432;  // Port number to listen on
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept incoming connection
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Start a new thread to handle each client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.out.println("Error in server: " + e.getMessage());
        }
    }
}

// ClientHandler class for handling multiple clients in separate threads
class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = input.readLine()) != null) {
                System.out.println("Received: " + message);
                output.println("Echo: " + message); // Echo response to client
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
