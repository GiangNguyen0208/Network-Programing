package rmi_de2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UCanAccessConnector {
	public static Connection getConnection() {
		Connection connection = null;
		String fileAccess = "C:\\Users\\COHOTECH.VN\\Documents\\Student.accdb";
//		String fileAccess = "C:\\Users\\Public\\Eclipse\\Lab_NetWork\\src\\rmi_demo\\NetWorkRMI\\access\\Student.accdb";
		String url = "jdbc:ucanaccess://" + fileAccess;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			connection = DriverManager.getConnection(url);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public static void main(String[] args) {
		Connection connection = getConnection();
		System.out.println(connection);
	}
}
