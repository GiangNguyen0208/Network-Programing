package socketTCP_de1;

import java.sql.SQLException;
import java.util.List;


public interface IUserDAO {
	boolean checkUsername(String username) throws SQLException;
	boolean login(String username, String password) throws SQLException;

}
