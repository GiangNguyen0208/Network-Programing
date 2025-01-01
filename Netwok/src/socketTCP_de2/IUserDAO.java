package socketTCP_de2;

import java.sql.SQLException;

public interface IUserDAO {
	boolean checkUsername(String username) throws SQLException;
	boolean login(String username, String password) throws SQLException;
}