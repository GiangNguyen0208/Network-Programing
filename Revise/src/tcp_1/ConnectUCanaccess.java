package tcp_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectUCanaccess {
	public static Connection getConnection() {
		Connection connection = null;
		try {
			String file = "C:\\Users\\COHOTECH.VN\\Documents\\products.accdb";
			String url = "jdbc:ucanaccess://" + file;
			connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public static void main(String[] args) {
		System.out.println(getConnection());
	}
}