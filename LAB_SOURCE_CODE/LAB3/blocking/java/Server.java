import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.net.InetSocketAddress;
import java.util.Iterator;

public class Server {
    public static void main(String[] args) {
        try {
            // Create a Selector
            Selector selector = Selector.open();

            // Create a ServerSocketChannel (non-blocking)
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            // Bind the server to a port
            InetSocketAddress hostAddress = new InetSocketAddress("127.0.0.1", 65432);
            serverSocketChannel.bind(hostAddress);

            // Register the server channel with the selector for accepting connections
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server is listening on port 65432...");

            // Event loop
            while (true) {
                // Wait for an event to occur (e.g., a new connection or incoming data)
                int numKeys = selector.select();
                if (numKeys == 0) {
                    continue; // No events to process, continue the loop
                }

                // Get the keys of the events that occurred
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove(); // Remove the key from the selectedKeys set to prevent re-processing

                    if (key.isAcceptable()) {
                        // Accept the new connection
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverChannel.accept();

                        // Make the new socket non-blocking
                        clientChannel.configureBlocking(false);

                        // Register the client channel with the selector for reading data
                        clientChannel.register(selector, SelectionKey.OP_READ);

                        System.out.println("New client connected: " + clientChannel.getRemoteAddress());
                    }

                    if (key.isReadable()) {
                        // Read data from the client
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = clientChannel.read(buffer);

                        if (bytesRead == -1) {
                            // Connection was closed by the client
                            System.out.println("Connection closed by client.");
                            clientChannel.close();
                        } else if (bytesRead > 0) {
                            // Flip the buffer before reading the data
                            buffer.flip();
                            String message = new String(buffer.array(), 0, buffer.limit());
                            System.out.println("Received from client: " + message);

                            // Echo the data back to the client
                            buffer.clear();
                            buffer.put(("Echo: " + message).getBytes());
                            buffer.flip();
                            clientChannel.write(buffer);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
