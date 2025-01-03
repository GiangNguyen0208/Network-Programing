package tcp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO{

	@Override
	public boolean checkUsername(String username) throws SQLException {
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "SELECT * FROM users WHERE username = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}

	@Override
	public boolean checkLogin(String username, String password) throws SQLException {
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}
	public static void main(String[] args) throws SQLException {
		UserDAO dao = new UserDAO();
		System.out.println(dao.checkUsername("user1"));
	}

}
