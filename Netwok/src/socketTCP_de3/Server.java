package socketTCP_de3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import socketTCP_de3.ServerHandling;

public class Server {
    private static final int PORT = 6969; 
    
    public static void main(String[] args) throws IOException {
    	ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

            while (true) {
                System.out.println("Waiting for a client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Tạo một luồng xử lý cho client
                ServerHandling clientHandler = new ServerHandling(clientSocket);
                clientHandler.start();
            }
    }
}
