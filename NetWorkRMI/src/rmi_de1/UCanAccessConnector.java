package rmi_de1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UCanAccessConnector {
	public static Connection getConnection() {
		Connection conn = null;
		String fileAccess = "C:\\Users\\Public\\Eclipse\\Lab_NetWork\\src\\rmi_demo\\NetWorkRMI\\access\\Student.accdb";
		String url = "jdbc:ucanaccess://" + fileAccess;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
		Connection conn = getConnection();
		System.out.println(conn);
	}
}
