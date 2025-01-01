package rmi_de1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends UnicastRemoteObject implements IUserDAO{

	protected UserDAO() throws RemoteException {
	}

	@Override
	public boolean checkUsername(String username) throws RemoteException {
		boolean isUsername = false;
		Connection conn = JDBCConnector.getConnection();
		String sql = "Select * From User Where username = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			isUsername = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUsername;
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		boolean isLogin = false;
		Connection conn = JDBCConnector.getConnection();
		String sql = "Select * From User Where username = ? And password = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			isLogin = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isLogin;
	}

}
