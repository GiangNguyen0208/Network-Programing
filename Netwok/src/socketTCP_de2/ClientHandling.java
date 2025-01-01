package socketTCP_de2;

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
	BufferedReader in;
	PrintWriter out;
	Socket socket;
	UserDAO userDao;
	StudentDAO studentDAO;
	public ClientHandling(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
		userDao = new UserDAO();
		studentDAO = new StudentDAO();
	}
	@Override
	public void run() {
		out.println("WELCOME TO MANAGE PRODUCT SYSTEM");
		boolean isLogin = false;
		String userInput = "";
		String status ="";
		String username = "", password = "";
		List<Student> students = new ArrayList<>();
		
		while (!isLogin) {
			try {
				userInput = in.readLine();
				StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
				String cmd = tokenizer.nextToken().toUpperCase();
				String param = tokenizer.nextToken();
				if("EXIT".equalsIgnoreCase(userInput)) break;
				switch (cmd) {
				case "USER": {
					username = param;
					status = (userDao.checkUsername(username)) ? "OK" : "FALSE";
					out.println(status);
					break;
				}
				case "PASS": {
					password = param;
					status = (userDao.login(username, password)) ? "OK" : "FALSE";
					out.println(status);
					break;
				}
				default:
					out.println("WRONG CMD");
				}
			} catch (IOException | SQLException e) {
				out.println("WRONG CMD");
			}
		}
		
		while (isLogin) {
			try {
				userInput = in.readLine();
				StringTokenizer tokenizer = new StringTokenizer(userInput, "\t");
				String cmd = tokenizer.nextToken();
				List<String> parameters = new ArrayList<>();
				while (tokenizer.hasMoreTokens()) {
					parameters.add(tokenizer.nextToken());
				}
				if("EXIT".equalsIgnoreCase(userInput)) break;
				
				switch (cmd) {
				case "VIEW": {
					students = studentDAO.viewAllStudents(parameters.get(0));
					for (Student student : students) {
						out.print(student.toString());
					}
					out.println("END");
					break;
				}
				case "FBID": {
					students = studentDAO.findStudentByID(Integer.parseInt(parameters.get(0)));
					for (Student student : students) {
						out.print(student.toString());
					}
					out.println("END");
					break;
				}
				case "FBN": {
					students = studentDAO.findStudentByName(parameters.get(0));
					for (Student student : students) {
						out.print(student.toString());
					}
					out.println("END");
					break;
				}
				case "ADD": {
					Student newStudent = new Student(Integer.parseInt(parameters.get(0)), parameters.get(1), Integer.parseInt(parameters.get(2)), Double.parseDouble(parameters.get(3)));
					status = (studentDAO.add(newStudent)) ? "OK" : "CAN NOT UPDATE";
					out.print(status);
					break; 
				}
				default:
					out.print("WRONG CMD");
				}
			} catch (IOException | NumberFormatException | SQLException e) {
				out.print("WRONG CMD");
			}
			
		}
		
		out.println(status);
		
	}
	private boolean login(String username, String password) throws SQLException {
		return userDao.login(username, password);
	}
	private boolean checkUsername(String username) throws SQLException {
		return userDao.checkUsername(username);
	}
}
