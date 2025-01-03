package tcp_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket;
		UserDAO userDAO = new UserDAO();
		StudentDAO studentDAO = new StudentDAO();
		try {
			socket = new Socket("localhost", 6677);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			
			String response = in.readLine();
			System.out.println(response);
			
			String sessionID = "", username ="", password = "";
			String status = "";
			String userInput = "";
			String userInputConsole = "";
			List<Student> students = new ArrayList<>();
				while (sessionID.isEmpty()) {
					System.out.println("Enter command (EXIT Command to Exit): ");
					try {
						userInputConsole = console.readLine();
						out.println(userInputConsole);
						if (userInputConsole.equalsIgnoreCase("EXIT")) {
							System.out.println("EXIT SYSTEM...");
							return;
						}
						StringTokenizer tokenizer = new StringTokenizer(userInputConsole, "\t");
						String command = tokenizer.nextToken().toUpperCase();
						String param = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
						
						switch (command) {
						case "USER": {
							username = param;
							status = (userDAO.checkUsername(username)) ? "OK" : "FALSE";
							System.out.println("CHECK USERNAME: " + status);
							break;
						}
						case "PASS": {
							password = param;
							boolean isLogin = userDAO.checkLogin(username, password);
							status = (isLogin) ? "OK" : "FALSE";
							sessionID = userDAO.createSessionID(username);
							System.out.println("CHECK LOGIN: " + status + " WITH SESSIONID: " + sessionID);
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
					System.out.println("Enter command (QUIT Command to Exit): ");
					try {
						userInputConsole = console.readLine();
						out.println(userInputConsole);
						if (userInputConsole.equalsIgnoreCase("QUIT")) {
							userDAO.removeSession(sessionID);
							sessionID = null;
							System.out.println("QUIT SYSTEM...");
							return;
						}
						
						if (userDAO.checkSessionID(sessionID)) {
							System.out.println(sessionID + " SESSIONID IS VALID");
						} else {
							sessionID = null;
							System.out.println("SESSIONID IS INVALID");
							break;
						}
						
						StringTokenizer tokenizer = new StringTokenizer(userInputConsole, "\t");
						String command = tokenizer.nextToken().toUpperCase();
						List<String> parameters = new ArrayList<>();
						while (tokenizer.hasMoreTokens()) {
							parameters.add(tokenizer.nextToken());
						}
						
						switch (command) {
						case "ADD": {
							int id = Integer.valueOf(parameters.get(0));
							String name = parameters.get(1);
							int year = Integer.valueOf(parameters.get(2));
							double grade = Double.valueOf(parameters.get(3));
							boolean isAdd = studentDAO.add(new Student(id, name, year, grade));
							status = isAdd ? "OK" : "FALSE";
							System.out.println("STATUS ADD: " + status);
							break;
						}
						case "EDIT": {
							int id = Integer.valueOf(parameters.get(0));
							String name = parameters.get(1);
							int year = Integer.valueOf(parameters.get(2));
							double grade = Double.valueOf(parameters.get(3));
							boolean isUpdate = studentDAO.edit(new Student(id, name, year, grade));
							status = isUpdate ? "OK" : "FALSE";
							System.out.println("STATUS EDIT: " + status);
							break;
						}
						case "REMOVE": {
							int count = 0;
							for (String string : parameters) {
								if (studentDAO.remove(Integer.valueOf(string))) {
									count++;
								}
							}
							System.out.println(count + " STUDENT IS DELETED");
							break;
						}
						case "FBID": {
							int id = Integer.valueOf(parameters.get(0));
							students = studentDAO.fbid(id);
							for (Student s : students) {
								System.out.println(s.toString());
							}
							break;
						}
						case "FBN": {
							String name = parameters.get(0);
							students = studentDAO.fbn(name);
							for (Student s : students) {
								System.out.println(s.toString());
							}
							break;
						}
						case "VIEW": {
							String tableStudent = parameters.get(0);
							students = studentDAO.view(tableStudent);
							for (Student s : students) {
								System.out.println(s.toString());
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
			e.printStackTrace();
		}
	}
}
