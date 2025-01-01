package socketTCP_de2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO{
	
	@Override
	public boolean checkUsername(String username) throws SQLException {
		Connection conn = UcanAccessConnector.getConnection();
		String sql = "SELECT * FROM User WHERE username = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, username);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}

	@Override
	public boolean login(String username, String password) throws SQLException {
		Connection conn = UcanAccessConnector.getConnection();
		String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(1, username);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}
	
}
