package tcp_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UCanAccessConnector {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String file = "C:\\Users\\COHOTECH.VN\\Documents\\Student.accdb";
			String url = "jdbc:ucanaccess://" + file;
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
		System.out.println(getConnection());
	}
}
