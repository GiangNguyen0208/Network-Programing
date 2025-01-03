package tcp_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDAO implements IUser {
	
	Map<String, String> map = new HashMap<>();

	@Override
	public boolean checkUsername(String username) throws SQLException {
		Connection connection = ConnectUCanaccess.getConnection();
		String sql = "Select * From users Where username = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}

	@Override
	public boolean checkLogin(String username, String password) throws SQLException {
		Connection connection = ConnectUCanaccess.getConnection();
		String sql = "Select * From users Where username = ? And password = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet rs = preparedStatement.executeQuery();
		return rs.next();
	}

	@Override
	public String createSessionID(String username) {
		String sessionID = UUID.randomUUID().toString();
		map.put(sessionID, username);
		return sessionID;
	}

	@Override
	public boolean checkSessionID(String sessionID) {
		return map.containsKey(sessionID);
	}

	@Override
	public void logout(String sessionID) {
		if (map.containsKey(sessionID)) {
			map.remove(sessionID);
		}
	}
	
}
