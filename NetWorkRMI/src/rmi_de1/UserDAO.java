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
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "Select * From User Where username = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			isUsername = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUsername;
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		boolean isLogin = false;
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "Select * From User Where username = ? And password = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet rs = preparedStatement.executeQuery();
			isLogin = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isLogin;
	}


}
