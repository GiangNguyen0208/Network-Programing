package tcp;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

public class ServerThread extends Thread {
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	UserDAO userDAO;
	ProductDAO productDAO;
	
	public ServerThread(Socket socket) throws IOException {
		this.socket = socket;
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
		userDAO = new UserDAO();
		productDAO = new ProductDAO();
	}
	@Override
	public void run() {
		output.println("WELCOME TO MANAGE PRODUCT SYSTEM !!!");
		String userInput = "";
		boolean isLogin = false;
		String status = "";
		String username = "", password = "";
		while (!isLogin) {
			try {
				userInput = input.readLine();
				if (userInput.equals("EXIT"))
					break;
				try {
					StringTokenizer stringTokenizer = new StringTokenizer(userInput, "\t");
					String command = stringTokenizer.nextToken().toUpperCase();
					String parameter = stringTokenizer.nextToken();
					switch (command) {
					case "USER":
						username = parameter;
						status = userDAO.checkUsername(username) ? "OK" : "FALSE";
						output.println(status);
						break;
					case "PASS":
						password = parameter;
						status = userDAO.checkLogin(username, password) ? "OK" : "FALSE";
						if (status.equals("OK"))
							isLogin = true;
						output.println(status);
						break;
					default:
						output.println("Sai cau lenh");
					}
				} catch (NoSuchElementException | SQLException e) {
					output.println("Sai cau lenh");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				output.println("Sai cau lenh");
			}
		}
		while (isLogin) {
			try {
				userInput = input.readLine();
				if (userInput.equals("QUIT"))
					break;
				try {
					StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
					String command = tokenizer.nextToken().toUpperCase();
					List<String> parameters = new ArrayList<>();
					while (tokenizer.hasMoreTokens()) {
						parameters.add(tokenizer.nextToken());
					}
					switch (command) {
					case "ADD":
						boolean isAdded = productDAO.add(new Product(Integer.valueOf(parameters.get(0)), parameters.get(1),
								Double.valueOf(parameters.get(2)), Integer.valueOf(parameters.get(3))));
						status = (isAdded) ? "OK" : "FAIL";
						output.println("Status Add: " + status);
						break;
					case "UPDATE":
						boolean isUpdate = productDAO.update(new Product(Integer.valueOf(parameters.get(0)), parameters.get(1),
								Double.valueOf(parameters.get(2)), Integer.valueOf(parameters.get(3))));
						status = (isUpdate) ? "OK" : "FAIL";
						output.println("Status Update: " + status);
						break;
					case "REMOVE":
						int count = 0;
						for (String string : parameters) {
							if (productDAO.remove(Integer.valueOf(string))) {
								count++;
							}
						}
						output.println(count+" products deleted !");
						break;
					case "VIEW":
						List<Product> products = productDAO.view(parameters.get(0));
					    StringBuilder response = new StringBuilder("Product List:\n");
					    if (products.size() > 0) {
					        for (Product product : products) {
					            response.append(product.toString()).append("\n");
					        }
					    } else {
					        response.append("No products found.\n");
					    }
					    response.append("THE END");
					    output.println(response.toString());
					    break;
					default:
						output.println("Sai cau lenh");
					}
				} catch (NoSuchElementException | SQLException e) {
					output.println("Sai cau lenh");
				}
			} catch (IOException e) {
				output.println("Sai cau lenh");
			}
		}
	}
}
