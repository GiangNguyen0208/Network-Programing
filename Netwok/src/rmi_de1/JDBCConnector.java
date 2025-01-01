package rmi_de1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnector {
	public static Connection getConnection() {
		Connection conn = null;
		String path = "C:\\Users\\COHOTECH.VN\\Documents\\Student.accdb";
		String url = "jdbc:ucanaccess://" + path;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
		Connection con = JDBCConnector.getConnection();
		System.out.println(con);
	}
}
