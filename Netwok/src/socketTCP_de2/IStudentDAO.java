package socketTCP_de2;

import java.sql.SQLException;
import java.util.List;

public interface IStudentDAO {
	public List<Student> findStudentByID(int id) throws SQLException ;
	public List<Student> findStudentByName(String name) throws SQLException ;
	public boolean add(Student student) throws SQLException ;
	public List<Student> viewAllStudents(String tablename) throws SQLException;
}
