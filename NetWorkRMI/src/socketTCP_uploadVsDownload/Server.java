package socketTCP_uploadVsDownload;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static final int port = 12345;
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			Transport tr = new Transport(clientSocket);
			tr.start();
		}
	}

}
