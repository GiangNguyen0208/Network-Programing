package rmi_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Client {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		try {
			Registry registry = LocateRegistry.getRegistry(6677);
			IUser userDAO = (IUser) registry.lookup("userDAO");
			IStudent studentDAO =  (IStudent) registry.lookup("studentDAO");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("WELCOM TO STUDENT SYSTEM");
			String userInput = "";
			String username = "", password ="", status = "";
			String sessionID = "";
			List<Student> students = new ArrayList<>();
			
			while (sessionID.isEmpty()) {
				System.out.println("Enter Command (EXIT Command to Exit): ");
				userInput = input.readLine();
				if (userInput.equalsIgnoreCase("EXIT")) {
					System.out.println("SYSTEM EXIT...");
					return;
				}
				StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
				String cmd = tokenizer.nextToken().toUpperCase();
				String param = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
				switch (cmd) {
				case "USER": {
					if (param.isEmpty()) {
						System.out.println("Vui lòng nhập username !!!");
					}
					username = param;
					status = (userDAO.checkUsername(username)) ? "OK" : "FALSE";
					System.out.println("Username Check: "+status);
					break;
				}
				case "PASS": {
					if (param.isEmpty()) {
						System.out.println("Vui lòng nhập password !!!");
					}
					password = param;
					status = (userDAO.checkUsername(username)) ? "OK" : "FALSE";
					sessionID = (userDAO.createSessionID(username));
					System.out.println("Login Status: " + status + ", with SESSIONID: " + sessionID);
					break;
				}
				default:
					System.out.println("WRONG COMMAND");
				}
			}
			while (!sessionID.isEmpty()) {
				System.out.println("Enter Command (QUIT Command to Exit): ");
				userInput = input.readLine();
				if (userInput.equalsIgnoreCase("QUIT")) {
					userDAO.logout(sessionID);
					sessionID = "";
					System.out.println("SYSTEM QUIT... + Your SessionID: " + sessionID);
					return;
				}
				
				if (!userDAO.checkSessionID(sessionID)) {
					System.out.println("Your SessionID: " + sessionID + " is INVALID");
					sessionID = "";
				} else {
					System.out.println("Your SessionID: " + sessionID + " is VALID");
				}
						
				StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
				if (!tokenizer.hasMoreTokens()) {
                    System.out.println("Invalid command. Please try again.");
                    continue;
                }
				String cmd = tokenizer.nextToken().toUpperCase();
				List<String> parameters = new ArrayList<>();
				while (tokenizer.hasMoreTokens()) {
					parameters.add(tokenizer.nextToken());
				}
				switch (cmd) {
				case "ADD": {
					if (parameters.size() < 4) {
						System.out.println("Vui lòng nhập đủ thông tin !!!");
						break;
					}
					int id = Integer.valueOf(parameters.get(0));
					String name = parameters.get(1);
					int year = Integer.valueOf(parameters.get(2));
					double grade = Double.valueOf(parameters.get(3));
					
					boolean isAdd = studentDAO.add(new Student(id, name, year, grade));
					status = isAdd ? "OK" : "FALSE";
					System.out.println("ADD STATUS: " + status);
					break;
				}
				case "EDIT": {
					if (parameters.size() < 4) {
						System.out.println("Vui lòng nhập đủ thông tin !!!");
						break;
					}
					int id = Integer.valueOf(parameters.get(0));
					String name = parameters.get(1);
					int year = Integer.valueOf(parameters.get(2));
					double grade = Double.valueOf(parameters.get(3));
					boolean isEdit = studentDAO.add(new Student(id, name, year, grade));
					status = isEdit ? "OK" : "FALSE";
					System.out.println("EDIT STATUS: " + status);
					break;
				}
				case "REMOVE": {
					int count = 0;
					for (String string : parameters) {
						if (studentDAO.remove(Integer.valueOf(string))) {
							count++;
						}
					}
					System.out.println(count + " Student is deleted");
					break;
				}
				case "VIEW": {
					if (parameters.size() < 1) {
						System.out.println("Vui lòng nhập đủ thông tin !!!");
						break;
					}
					students = studentDAO.view(parameters.get(0));
					for (Student s : students) {
						System.out.println(s.toString());
					}
					break;
				}
				case "FBN": {
					if (parameters.size() < 1) {
						System.out.println("Vui lòng nhập đủ thông tin !!!");
						break;
					}
					students = studentDAO.fbn(parameters.get(0));
					for (Student s : students) {
						System.out.println(s.toString());
					}
					break;
				}
				case "FBID": {
					if (parameters.size() < 1) {
						System.out.println("Vui lòng nhập đủ thông tin !!!");
						break;
					}
					students = studentDAO.fbid(Integer.parseInt(parameters.get(0)));
					for (Student s : students) {
						System.out.println(s.toString());
					}
					break;
				}
				default:
					System.out.println("WRONG COMMAND");
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("WRONG COMMAND");
		}
		
	}
}
