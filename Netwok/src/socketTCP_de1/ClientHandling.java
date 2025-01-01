package socketTCP_de1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ClientHandling extends Thread {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	IUserDAO userDAO = new UserDAO();
	IStudentDAO studentDAO = new StudentDAO();
	
	public ClientHandling(Socket socket) throws IOException {
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	@Override
	public void run() {
		out.println("WELCOME TO MANAGE PRODUCT SYSTEM");
		String input = "";
		String username = "";
		String password = "";
		String status = "";
		String id, name;
		boolean isLogin = false;
		List<Student> list = new ArrayList<>();
		
		while (!isLogin) {
			try {
				input = in.readLine();
				if (input.equals("EXIT")) break;
				try {
					StringTokenizer stringTokenizer = new StringTokenizer(input, "\t");
					String command = stringTokenizer.nextToken().toUpperCase();
					String parameter = stringTokenizer.nextToken();
					switch (command) {
					case "USER":
						username = parameter;
						status = userDAO.checkUsername(username) ? "OK" : "FALSE";
						out.println(status);
						break;
					case "PASS":
						password = parameter;
						status = userDAO.login(username, password) ? "OK" : "FALSE";
						if (status.equals("OK"))
							isLogin = true;
						out.println(status);
						break;
					default:
						out.println("Sai cau lenh");
					}
				} catch (NoSuchElementException | SQLException e) {
					out.println("Sai cau lenh");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while (isLogin) {
			try {
				input = in.readLine();
				if (input.equals("QUIT")) break;
				try {
					StringTokenizer stringTokenizer = new StringTokenizer(input, "\t");
					String command = stringTokenizer.nextToken().toUpperCase();
					List<String> parameters = new ArrayList<>();
					while (stringTokenizer.hasMoreTokens()) {
						parameters.add(stringTokenizer.nextToken());
					}
					switch (command) {
					case "ADD":
						boolean isAdded = studentDAO.createNewStudent(new Student(Integer.parseInt(parameters.get(0)), parameters.get(1),
								Integer.valueOf(parameters.get(2)), Double.valueOf(parameters.get(3))));
						status = (isAdded) ? "Student was created.! :)" : "Create Fail.! :(";
						out.println(status);
						break;
					case "VIEW":
						List<Student> students = studentDAO.view(parameters.get(0));
						if (students.size() > 0) {
							for (Student student : students) {
								out.println(student.toString());
							}
						}
						break;
					case "FBID":
						id = parameters.get(0);
						list = studentDAO.findStudentByID(Integer.parseInt(id));
						out.println("Thông tin danh sách sinh viên có id: " + id);
						for (Student student : list) {
							out.println(student.toString());
							System.out.println(student.toString());
						}
						break;
					case "FBN":
						name = parameters.get(0);
						list = studentDAO.findStudentByName(name);
						out.println("Thông tin danh sách sinh viên có id: " + name);
						for (Student student : list) {
							out.println(student.toString());
							System.out.println(student.toString());
						}
						break;
					default:
						out.println("Wrong cmd");
					}
				} catch (NoSuchElementException | SQLException e) {
					out.println("Wrong cmd");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
