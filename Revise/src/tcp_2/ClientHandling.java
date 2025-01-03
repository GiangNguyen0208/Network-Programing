package tcp_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ClientHandling extends Thread{
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	UserDAO userDAO;
	StudentDAO studentDAO;
	public ClientHandling(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		userDAO = new UserDAO();
		studentDAO = new StudentDAO();
	}
	@Override
	public void run() {
		out.println("WELCOME TO STUDENT SYSTEM");
		String sessionID = "", username ="", password = "";
		String status = "";
		String userInput = "";
		List<Student> students = new ArrayList<>();
		
			while (sessionID.isEmpty()) {
				out.println("Enter command (EXIT Command to Exit): ");
				try {
					userInput = in.readLine();
					if (userInput.equalsIgnoreCase("EXIT")) {
						out.println("EXIT SYSTEM...");
						return;
					}
					StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
					String command = tokenizer.nextToken().toUpperCase();
					String param = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
					
					switch (command) {
					case "USER": {
						username = param;
						status = (userDAO.checkUsername(username)) ? "OK" : "FALSE";
						out.print("CHECK USERNAME: " + status);
						break;
					}
					case "PASS": {
						password = param;
						boolean isLogin = userDAO.checkLogin(username, password);
						status = (isLogin) ? "OK" : "FALSE";
						sessionID = userDAO.createSessionID(username);
						out.print("CHECK LOGIN: " + status + " WITH SESSIONID: " + sessionID);
						break;
					}
					default:
						out.println("WRONG CMD");
					}
				} catch (Exception e) {
					out.println("WRONG CMD");
				}
			}
			
			while (!sessionID.isEmpty()) {
				out.println("Enter command (EXIT Command to Exit): ");
				try {
					userInput = in.readLine();
					if (userInput.equalsIgnoreCase("QUIT")) {
						userDAO.removeSession(sessionID);
						sessionID = null;
						out.println("QUIT SYSTEM...");
						return;
					}
					
					if (userDAO.checkSessionID(sessionID)) {
						out.println(sessionID + " SESSIONID IS VALID");
					} else {
						sessionID = null;
						out.println("SESSIONID IS INVALID");
						break;
					}
					
					StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
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
						out.println("STATUS ADD: " + status);
						break;
					}
					case "EDIT": {
						int id = Integer.valueOf(parameters.get(0));
						String name = parameters.get(1);
						int year = Integer.valueOf(parameters.get(2));
						double grade = Double.valueOf(parameters.get(3));
						boolean isUpdate = studentDAO.edit(new Student(id, name, year, grade));
						status = isUpdate ? "OK" : "FALSE";
						out.println("STATUS EDIT: " + status);
						break;
					}
					case "REMOVE": {
						int count = 0;
						for (String string : parameters) {
							if (studentDAO.remove(Integer.valueOf(string))) {
								count++;
							}
						}
						out.println(count + " STUDENT IS DELETED");
						break;
					}
					case "FBID": {
						int id = Integer.valueOf(parameters.get(0));
						students = studentDAO.fbid(id);
						for (Student s : students) {
							out.print(s.toString());
						}
						break;
					}
					case "FBN": {
						String name = parameters.get(0);
						students = studentDAO.fbn(name);
						for (Student s : students) {
							out.print(s.toString());
						}
						break;
					}
					case "VIEW": {
						String tableStudent = parameters.get(0);
						students = studentDAO.view(tableStudent);
						for (Student s : students) {
							out.print(s.toString());
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
