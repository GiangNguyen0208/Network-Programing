package socketTCP_de2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UcanAccessConnector {
	static Connection connection;
	static {
		String file = "C:\\Users\\COHOTECH.VN\\Documents\\Student.accdb";
		String url = "jdbc:ucanaccess://" + file;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			connection = DriverManager.getConnection(url);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		return connection;
	}
}
