package tcp;

import java.sql.SQLException;

public interface IUserDAO {
	public boolean checkUsername(String username) throws SQLException;
	public boolean checkLogin(String username, String password) throws SQLException;
}
