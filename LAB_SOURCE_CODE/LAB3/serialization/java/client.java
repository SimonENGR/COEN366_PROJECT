import java.io.*;
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

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            // Create and serialize the student object
            Student student = new Student(101, "Alice", "A");
            objectOutputStream.writeObject(student);

            System.out.println("Serialized object sent to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
