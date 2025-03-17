import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    int id_number;
    String name;
    String grade;

    public Student(int id_number, String name, String grade) {
        this.id_number = id_number;
        this.name = name;
        this.grade = grade;
    }
}

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is listening...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            // Receive the serialized object
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Student student = (Student) objectInputStream.readObject();

            System.out.println("Received Student: ID " + student.id_number + 
                               ", Name " + student.name + ", Grade " + student.grade);

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
