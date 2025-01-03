package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket;
		try {
			socket = new Socket("localhost", 8020);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			
			// Nhận thông báo từ server
			System.out.println("Connected to server");
            String response;
            
            while ((response = input.readLine()) != null) {
            	System.out.println(response);
            	
            	String userCommand = console.readLine();
            	output.println(userCommand);
            	continue;
            	
            }
            System.out.println(response);
			
		} catch (Exception e) {
			System.out.println("WRONG CMD");
		}
		
	}
}
