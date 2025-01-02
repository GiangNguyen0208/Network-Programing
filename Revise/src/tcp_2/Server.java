package tcp_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(6677);
		while (true) {
			System.out.println("Server is running...");
			Socket socket = serverSocket.accept();
			ClientHandling client = new ClientHandling(socket);
			client.start();
			
		}
		
	}
}	