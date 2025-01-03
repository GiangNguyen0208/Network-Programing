package tcp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UCanAccessConnector {
	public static Connection getConnection() {
		Connection connection = null;
		String file = "C:\\Users\\Public\\Eclipse\\Lab_NetWork\\src\\rmi_demo\\Revise\\access\\products.accdb";
		String url = "jdbc:ucanaccess://" + file;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			connection = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	public static void main(String[] args) {
		Connection conn = getConnection();
		System.out.println(conn);
	}
}
