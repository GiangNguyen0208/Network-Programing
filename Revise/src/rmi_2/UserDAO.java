package rmi_2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserDAO extends UnicastRemoteObject implements IUser {
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> map = new HashMap<>();

	protected UserDAO() throws RemoteException {
	}

	@Override
	public boolean checkUsername(String username) throws RemoteException {
		Connection connection = ConnectorUcanaccess.getConnection();
		String query = "Select * From User Where username = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkLogin(String username, String password) throws RemoteException {
		Connection connection = ConnectorUcanaccess.getConnection();
		String query = "Select * From User Where username = ? And password";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String createSessionID(String username) throws RemoteException {
		String sessionID = UUID.randomUUID().toString();
		map.put(sessionID, username);
		return sessionID;
	}

	@Override
	public boolean checkSessionID(String session) throws RemoteException {
		return map.containsKey(session);
	}

	@Override
	public void logout(String session) throws RemoteException {
		if (map.containsKey(session)) {
			map.remove(session);
		}
	}
}
