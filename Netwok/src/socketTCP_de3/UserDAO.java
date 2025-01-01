package socketTCP_de3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO{
	@Override
	public boolean checkUsername(String username) throws SQLException {
		Connection connection = UcanaccessConnector.getConnection();
		String query = "Select * From users where username = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, username);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}

	@Override
	public boolean login(String username, String password) throws SQLException {
		Connection connection = UcanaccessConnector.getConnection();
		String query = "Select * From users where username = ? And password = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}

}
