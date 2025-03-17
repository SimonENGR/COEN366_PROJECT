import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.net.InetSocketAddress;
import java.util.Iterator;

public class Client {
    public static void main(String[] args) {
        try {
            // Create a Selector
            Selector selector = Selector.open();

            // Create a SocketChannel (non-blocking)
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            // Connect to the server
            InetSocketAddress hostAddress = new InetSocketAddress("127.0.0.1", 65432);
            socketChannel.connect(hostAddress);

            // Register the socket channel with the selector for connection and read events
            socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

            System.out.println("Client started, waiting to connect...");

            // Event loop
            while (true) {
                // Wait for an event to occur (e.g., connection or incoming data)
                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

                while (selectedKeys.hasNext()) {
                    SelectionKey key = selectedKeys.next();
                    selectedKeys.remove(); // Remove the key from the selectedKeys set to prevent re-processing

                    if (key.isConnectable()) {
                        // Handle connection event
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        if (clientChannel.isConnectionPending()) {
                            clientChannel.finishConnect();
                            System.out.println("Connected to server!");
                        }
                    }

                    if (key.isReadable()) {
                        // Read data from the server
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = clientChannel.read(buffer);

                        if (bytesRead == -1) {
                            // Connection was closed by the server
                            System.out.println("Connection closed by server.");
                            clientChannel.close();
                        } else if (bytesRead > 0) {
                            // Flip the buffer before reading the data
                            buffer.flip();
                            String response = new String(buffer.array(), 0, buffer.limit());
                            System.out.println("Received from server: " + response);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
