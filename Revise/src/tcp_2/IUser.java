package tcp_2;

import java.sql.SQLException;

public interface IUser {
	public boolean checkUsername(String username) throws SQLException;
	public boolean checkLogin(String username, String password) throws SQLException;
	public String createSessionID(String username);
	public boolean checkSessionID(String sessionID);
	public void removeSession(String sessionID);
}
