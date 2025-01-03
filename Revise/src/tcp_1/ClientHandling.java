package tcp_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class ClientHandling extends Thread {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	UserDAO userDAO;
	ProductDAO productDAO;
	
	public ClientHandling(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		userDAO = new UserDAO();
		productDAO = new ProductDAO();
	}
	
	@Override
	public void run() {
		out.println("WELCOME TO PRODUCTS SYSTEM !!!");
		String userInput = "", username = "", password ="", sessionID ="";
		String status = "";
		List<Product> products = new ArrayList<>();
		
		while (sessionID.isEmpty()) {
			out.println("ENTER CMD (EXIT Command to EXIT): ");
			try {
				userInput = in.readLine();
				if (userInput.equalsIgnoreCase("EXIT")) {
					out.println("EXIT SYSTEM...");
					return;
				}
				StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
				String cmd = tokenizer.nextToken().toUpperCase();
				String param = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
				
				switch (cmd) {
				case "USER": {
					username = param;
					if (param.isEmpty()) {
						out.println("Vui lòng nhập đầy đủ thông tin !!!");
					}
					status = (userDAO.checkUsername(username)) ? "OK" : "FALSE";
					out.println("Check username: "+status);
					break;
				}
				case "PASS": {
					password = param;
					if (param.isEmpty()) {
						out.println("Vui lòng nhập đầy đủ thông tin !!!");
					}
					boolean isLogin = userDAO.checkLogin(username, password);
					sessionID = userDAO.createSessionID(username);
					if (isLogin)
						out.println("Check Login: OK" + " With SessionID: " + sessionID);
					else
						out.println("Check Login: Fail" + " With SessionID: {}");
					break;
				}
				default:
					out.println("WRONG CMD");
				}
			} catch (IOException | SQLException e) {
				out.println("WRONG CMD");
			}
			
		}
		while (!sessionID.isEmpty()) {
			out.println("ENTER CMD (QUIT Command to QUIT): ");
			try {
				userInput = in.readLine();
				
				if (userInput.equalsIgnoreCase("QUIT")) {
					userDAO.logout(sessionID);
					sessionID = "";
					out.println("SYSTEM QUIT... RESET SESSIONID: " + sessionID);
				}
				
				if (userDAO.checkSessionID(sessionID)) {
					out.println("SESSION IS VALID SESSIONID: " + sessionID);
				} else {
					sessionID = "";
					out.println("SESSION IS INVALID SESSIONID: " + sessionID);
				}
				
				StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
				String cmd = tokenizer.nextToken().toUpperCase();
				List<String> parameters = new ArrayList<>();
				while (tokenizer.hasMoreTokens()) {
					parameters.add(tokenizer.nextToken());
				}
				switch (cmd) {
				case "ADD": {
					if (parameters.size() < 4) {
						out.println("Vui lòng nhập đầy đủ thông tin !!!");
					}
					int id = Integer.valueOf(parameters.get(0));
					String name = parameters.get(1);
					double price = Double.valueOf(parameters.get(2));
					int quantity = Integer.valueOf(parameters.get(3));
					boolean isAdd = productDAO.add(new Product(id, name, price, quantity));
					status = isAdd ? "OK" : "FALSE";
					out.print("ADD STATUS: " + isAdd);
					break;
				}
				case "EDIT": {
					if (parameters.size() < 4) {
						out.println("Vui lòng nhập đầy đủ thông tin !!!");
					}
					int id = Integer.valueOf(parameters.get(0));
					String name = parameters.get(1);
					double price = Double.valueOf(parameters.get(2));
					int quantity = Integer.valueOf(parameters.get(3));
					boolean isUpdate = productDAO.edit(new Product(id, name, price, quantity));
					status = isUpdate ? "OK" : "FALSE";
					out.print("EDIT STATUS: " + isUpdate);
					break;
				}
				case "REMOVE": {
					int count = 0;
					for (String string : parameters) {
						if (productDAO.remove(Integer.valueOf(string))) {
							count++;
						}
					}
					out.print(count+" PRODUCT IS REMOVE");
					break;
				}
				case "VIEW": {
					if (parameters.size() < 1) {
						out.println("Vui lòng nhập đầy đủ thông tin !!!");
					}
					products = productDAO.view(parameters.get(0));
					for (Product product : products) {
						out.print(product.toString());
					}
					break;
				}
				case "FBN": {
					String name = parameters.get(0);
					products = productDAO.fbn(name);
					for (Product product : products) {
						out.print(product.toString());
					}
					break;
				}
				case "FBID": {
					int id = Integer.valueOf(parameters.get(0));
					products = productDAO.fbid(id);
					for (Product product : products) {
						out.print(product.toString());
					}
					break;
				}
				default:
					out.println("WRONG CMD");
				}
				
			} catch (Exception e) {
				out.println("WRONG CMD");
			}
		}
	}
}
