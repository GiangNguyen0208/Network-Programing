package socketTCP_de1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(6969);
		while (true) {
			Socket socketClient = serverSocket.accept();
			ClientHandling client = new ClientHandling(socketClient);
			client.start();
		}
	}
}
