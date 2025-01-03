package tcp_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		UserDAO userDAO = new UserDAO();
		ProductDAO productDAO = new ProductDAO();
		Socket socket;
		try {
			socket = new Socket("localhost", 5544);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			
			// Nhận thông báo từ server
			String response = input.readLine();
			System.out.println(response);
			String userInputConsole = "";
			String userInput = "", username = "", password ="", sessionID ="";
			String status = "";
			List<Product> products = new ArrayList<>();
			
			while (sessionID.isEmpty()) {
				try {
					System.out.println("Nhập lệnh");
					userInputConsole = console.readLine();
					output.println(userInputConsole);
					if (userInputConsole.equalsIgnoreCase("EXIT")) {
						System.out.println("EXIT SYSTEM...");
						return;
					}
					
					StringTokenizer tokenizer = new StringTokenizer(userInputConsole, "\t");
					String cmd = tokenizer.nextToken().toUpperCase();
					String param = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
					
					switch (cmd) {
					case "USER": {
						username = param;
						if (param.isEmpty()) {
							System.out.println("Vui lòng nhập đầy đủ thông tin !!!");
							break;
						}
						status = (userDAO.checkUsername(username)) ? "OK" : "FALSE";
						System.out.println("Check username: "+status);
						break;
					}
					case "PASS": {
						password = param;
			            if (param.isEmpty()) {
			                System.out.println("Vui lòng nhập đầy đủ thông tin !!!");
			                break;
			            }
			            boolean isLogin = userDAO.checkLogin(username, password);
			            if (isLogin) {
			                sessionID = userDAO.createSessionID(username);
			                System.out.println("Check Login: OK" + " With SessionID: " + sessionID);
			            } else {
			                System.out.println("Check Login: Fail");
			            }
			            break;
					}
					default:
						System.out.println("WRONG CMD");
					}
				} catch (Exception e) {
					System.out.println("WRONG CMD");
				}
			}
			
			while (!sessionID.isEmpty()) {
				System.out.println("ENTER CMD (QUIT Command to QUIT): ");
				try {
					userInputConsole = console.readLine();
					output.println(userInputConsole);
					if (userInputConsole.equalsIgnoreCase("QUIT")) {
						userDAO.logout(sessionID);
						sessionID = "";
						System.out.println("SYSTEM QUIT... RESET SESSIONID: " + sessionID);
					}
					
					if (userDAO.checkSessionID(sessionID)) {
						System.out.println("SESSION IS VALID SESSIONID: " + sessionID);
					} else {
						sessionID = null;
						System.out.println("SESSION IS INVALID SESSIONID: " + sessionID);
					}
					
					StringTokenizer tokenizer = new StringTokenizer(userInputConsole, "\t");
					String cmd = tokenizer.nextToken().toUpperCase();
					List<String> parameters = new ArrayList<>();
					while (tokenizer.hasMoreTokens()) {
						parameters.add(tokenizer.nextToken());
					}
					switch (cmd) {
					case "ADD": {
						if (parameters.size() < 4) {
							System.out.println("Vui lòng nhập đầy đủ thông tin !!!");
						}
						int id = Integer.valueOf(parameters.get(0));
						String name = parameters.get(1);
						double price = Double.valueOf(parameters.get(2));
						int quantity = Integer.valueOf(parameters.get(3));
						boolean isAdd = productDAO.add(new Product(id, name, price, quantity));
						status = isAdd ? "OK" : "FALSE";
						System.out.println("ADD STATUS: " + isAdd);
						break;
					}
					case "EDIT": {
						if (parameters.size() < 4) {
							System.out.println("Vui lòng nhập đầy đủ thông tin !!!");
						}
						int id = Integer.valueOf(parameters.get(0));
						String name = parameters.get(1);
						double price = Double.valueOf(parameters.get(2));
						int quantity = Integer.valueOf(parameters.get(3));
						boolean isUpdate = productDAO.edit(new Product(id, name, price, quantity));
						status = isUpdate ? "OK" : "FALSE";
						System.out.println("EDIT STATUS: " + status);
						break;
					}
					case "REMOVE": {
						int count = 0;
						for (String string : parameters) {
							if (productDAO.remove(Integer.valueOf(string))) {
								count++;
							}
						}
						System.out.println(count+" PRODUCT IS REMOVE");
						break;
					}
					case "VIEW": {
						if (parameters.size() < 1) {
							System.out.println("Vui lòng nhập đầy đủ thông tin !!!");
						}
						products = productDAO.view(parameters.get(0));
						for (Product product : products) {
							System.out.println(product.toString());
						}
						break;
					}
					case "FBN": {
						String name = parameters.get(0);
						products = productDAO.fbn(name);
						for (Product product : products) {
							System.out.println(product.toString());
						}
						break;
					}
					case "FBID": {
						int id = Integer.valueOf(parameters.get(0));
						products = productDAO.fbid(id);
						for (Product product : products) {
							System.out.println(product.toString());
						}
						break;
					}
					default:
						System.out.println("WRONG CMD");
					}
					
				} catch (Exception e) {
					System.out.println("WRONG CMD");
				}
			}
		} catch (Exception e) {
			System.out.println("WRONG CMD");
		}
	}
}