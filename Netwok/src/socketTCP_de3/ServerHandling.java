package socketTCP_de3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.StringTokenizer;

public class ServerHandling extends Thread {
	private Socket clientSocket; // Socket kết nối với client
    private BufferedReader in;
    private PrintWriter out;
    private UserDAO userDAO;

    // Constructor nhận socket client
    public ServerHandling(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Nhận dữ liệu từ client
        this.out = new PrintWriter(clientSocket.getOutputStream(), true); // Gửi dữ liệu tới client
        this.userDAO = new UserDAO();
    }

    @Override
    public void run() {
        try {
            out.println("WELCOME TO MANAGE PRODUCT SYSTEM");
            boolean isLogin = false;
            String username = "";
            String password = "";
            String status = "";

            while (!isLogin) {
                try {
                    out.print("Enter command: ");
                    String userIn = in.readLine(); // Đọc dữ liệu từ client
                    if (userIn == null || userIn.equalsIgnoreCase("EXIT")) {
                        out.println("Exiting system...");
                        break;
                    }

                    StringTokenizer tokenizer = new StringTokenizer(userIn, "\t");
                    String cmd = tokenizer.nextToken().toUpperCase();
                    String param = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

                    switch (cmd) {
                        case "USER": {
                            username = param;
                            status = (userDAO.checkUsername(username)) ? "OK" : "FALSE";
                            out.println("Username check: " + status);
                            break;
                        }
                        case "PASS": {
                            password = param;
                            status = (userDAO.login(username, password)) ? "OK" : "FALSE";
                            if ("OK".equals(status)) {
                                isLogin = true;
                            }
                            out.println("Password check: " + status);
                            break;
                        }
                        default:
                            out.println("WRONG CMD !!!");
                    }
                } catch (SQLException e) {
                    out.println("Database error: " + e.getMessage());
                }
            }

            if (isLogin) {
                out.println("Login successful! Welcome, " + username);
                try {
                	
                	
                	
                } catch (Exception e) {
                	out.println("WRONG CMD !!!");
				}
                
            }
        } catch (IOException e) {
        	out.println("WRONG CMD !!!");
        }
    }


}
