package socketTCP_de2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import socketTCP_de1.ClientHandling;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(6969);
		while (true) {
			System.out.println("Server is running...");
			Socket socketClient = serverSocket.accept();
			ClientHandling client = new ClientHandling(socketClient);
			client.start();
		}
	}
}
