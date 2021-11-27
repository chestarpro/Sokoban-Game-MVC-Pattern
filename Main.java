import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        new Viewer();
        int portNumber = 4446;
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
        } catch (IOException exception) {
            System.err.println("Error: " + exception);
        }
    }
}