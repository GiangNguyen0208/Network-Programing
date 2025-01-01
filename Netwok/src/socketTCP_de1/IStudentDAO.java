package socketTCP_de1;

import java.sql.SQLException;
import java.util.List;

public interface IStudentDAO {
	List<Student> findStudentByID(int id) throws SQLException;
	List<Student> findStudentByName(String name) throws SQLException;
	boolean createNewStudent(Student student) throws SQLException;
	List<Student> view(String table) throws SQLException;
}
