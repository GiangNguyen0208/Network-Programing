package rmi_de2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends UnicastRemoteObject implements IUser{
	

	protected UserDAO() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkUsername(String username) throws RemoteException {
		Connection connection = UCanAccessConnector.getConnection();
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
	public boolean login(String username, String password) throws RemoteException {
		Connection connection = UCanAccessConnector.getConnection();
		String query = "Select * From User Where username = ? And password = ?";
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
	public static void main(String[] args) throws RemoteException {
		UserDAO dao = new UserDAO();
		System.out.println(dao.checkUsername("user1"));
	}

}
