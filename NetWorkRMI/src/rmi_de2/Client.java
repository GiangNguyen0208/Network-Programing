package rmi_de2;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Client {
	public static void main(String[] args) throws RemoteException, NotBoundException {
			try {
				Registry registry = LocateRegistry.getRegistry("localhost", 9630);
				IUser userDAO = (IUser) registry.lookup("userDAO");
				IStudent studentDAO = (IStudent) registry.lookup("studentDAO");
				BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
				
				String userInput = "";
				String status ="";
				String username = "", password = "";
				boolean isLogin = false;
				
				System.out.println("WELCOME TO MANAGE PRODUCT SYSTEM");
				while (true) {
					userInput = line.readLine();
					if (!isLogin) {
						StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
						String cmd = tokenizer.nextToken().toUpperCase();
						String param = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
						if (cmd.equalsIgnoreCase("EXIT")) {
							System.out.println("EXIT System....");
							break;
						}
						switch (cmd) {
						case "USER": {
							username = param;
							status = (userDAO.checkUsername(username)) ? "OK" : "FALSE";
							System.out.println("Check Username: "+status);
							break;
						}
						case "PASS": {
							password = param;
                            isLogin = userDAO.login(username, password);
                            System.out.println("Login: " + (isLogin ? "OK" : "False"));
                            break;
						}
						case "CHECK": {
							System.out.println("Check Status Login: " + isLogin);
                            break;
						}
						default:
							System.out.println("WRONG CMD !!!");
						}
					} else {
						StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
						String cmd = tokenizer.nextToken().toUpperCase();
						List<String> parameters = new ArrayList<>();
						while (tokenizer.hasMoreTokens()) {
							parameters.add(tokenizer.nextToken());
						}
						if ("QUIT".equalsIgnoreCase(cmd)) {
							System.out.println("QUIT System...");
							break;
						}
						switch (cmd) {
						case "VIEW": {
							String studentTable = parameters.get(0);
							List<Student> students = studentDAO.view(studentTable);
							for (Student s : students) {
								System.out.println(s.toString());
							}
							break;
						}
						default:
							System.out.println("WRONG CMD !!!");
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
}
