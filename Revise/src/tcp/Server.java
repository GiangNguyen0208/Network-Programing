package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8020);
		while (true) {
			System.out.println("Server is running...");
			Socket clientSocket = serverSocket.accept();
			ServerThread client = new ServerThread(clientSocket);
			client.start();
		}
	}
}
