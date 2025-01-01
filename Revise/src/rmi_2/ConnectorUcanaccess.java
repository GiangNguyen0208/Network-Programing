package rmi_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorUcanaccess {
	public static Connection getConnection() {
		Connection connection = null;
		try {
			String file = "C:\\Users\\COHOTECH.VN\\Documents\\Student.accdb";
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
