package rmi_de1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Client {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(1234);
		IUserDAO userDAO = (IUserDAO) registry.lookup("userDAO");
		IStudentDAO studentDAO = (IStudentDAO) registry.lookup("studentDAO");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String userInput = "";
		boolean isLogin = false;
		String username ="", password ="";
		String status = "";
		System.out.println("WELCOME TO MANAGE PRODUCT SYSTEM");
		while (isLogin) {
			try {
				userInput = in.readLine();
				if (userInput == null || userInput.equalsIgnoreCase("EXIT")) {
					System.out.println("EXIT System....");
					break;
				}
				try {
					StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
					String cmd = tokenizer.nextToken().toUpperCase();
					String param = tokenizer.nextToken();
					
					switch (cmd) {
						case "USER": {
							username = param;
							status = (userDAO.checkUsername(username)) ? "OK" : "False";
							System.out.println("Check Username: " + status);
							break;
						}
						case "PASS": {
							password = param;
							status = (userDAO.login(username, password)) ? "OK" : "False";
							if (status.equalsIgnoreCase("OK"))
								isLogin = true;
							System.out.println("Login: " + status);
							break;
						}
						case "CHECK": {
							System.out.println("Check Status Login: " + isLogin);
							break;
						}
						default:
							System.out.println("WRONG CMD !!!");
						}
				} catch (Exception e) {
					System.out.println("WRONG CMD !!!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		while (!isLogin) {
			try {
				userInput = in.readLine();
				if (userInput == null || userInput.equalsIgnoreCase("QUIT")) {
					System.out.println("EXIT System....");
					break;
				}
				try {
					StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
					String cmd = tokenizer.nextToken().toUpperCase();
					List<String> parameters = new ArrayList<String>();
					while (tokenizer.hasMoreTokens()) {
						parameters.add(tokenizer.nextToken());
					}
					
					switch (cmd) {
						case "ADD": {
							Student student = new Student(Integer.valueOf(parameters.get(0)), parameters.get(1),
									Integer.valueOf(parameters.get(2)), Double.valueOf(parameters.get(3)));
							status = (studentDAO.add(student)) ? "OK" : "ERROR";
							System.out.println(status);
							break;
						}
						case "REMOVE": {
							status = (studentDAO.remove(Integer.parseInt(parameters.get(0)))) ? "OK" : "CAN NOT REMOVE";
							System.out.println(status);
							break;
						}
						case "EDIT": {
							Student student = new Student(Integer.parseInt(parameters.get(0)), parameters.get(1),
									Integer.parseInt(parameters.get(2)), Double.parseDouble(parameters.get(3)));
							status = (studentDAO.edit(student)) ? "OK" : "CAN NOT UPDATE";
							System.out.println(status);
							break;
						}
						case "VIEW": {
							List<Student> list = studentDAO.view(parameters.get(0));
							System.out.println("Danh sách Student có tên: " + parameters.get(0));
							for (Student student : list) {
								System.out.println(student.toString());
							}
							break;
						}
						default:
							System.out.println("WRONG CMD !!!");
						}
				} catch (Exception e) {
					System.out.println("WRONG CMD !!!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
