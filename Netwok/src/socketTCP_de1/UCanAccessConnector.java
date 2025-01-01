package socketTCP_de1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UCanAccessConnector {
	private static Connection conn;
	static {
		String file = "C:\\Users\\COHOTECH.VN\\Documents\\Student.accdb";
		String url = "jdbc:ucanaccess://" + file;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(url);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static void main(String[] args) {
		Connection connection = UCanAccessConnector.getConnection();
	}
	
	
	
}
