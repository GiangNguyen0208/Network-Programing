package socketTCP_de2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket("localhost", 6969);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			
			// Nhận thông báo từ server
			System.out.println("Connected to server");
            System.out.println("Server: " + input.read());
            String response;
            
            while ((response = input.readLine()) != null) {
            	System.out.println(response);
            	
            	String userCommand = console.readLine();
            	output.println(userCommand);
            	continue;
            }
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
