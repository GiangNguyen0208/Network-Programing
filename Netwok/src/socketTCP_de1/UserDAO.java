package socketTCP_de1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
	@Override
	public boolean login(String username, String password) throws SQLException {
		Connection conn = UCanAccessConnector.getConnection();
		String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}

	@Override
	public boolean checkUsername(String username) throws SQLException {
		Connection conn = UCanAccessConnector.getConnection();
		String sql = "SELECT * FROM User WHERE username = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, username);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}
	
	public static void main(String[] args) throws SQLException {
		IUserDAO userDAO = new UserDAO();
		boolean uservalid = userDAO.checkUsername("user3");
		if (uservalid) {
			System.out.println("Username Valid.");
		} else {
			System.out.println("Username InValid.");
		}
		
	}
	
}
