package tcp_2;

import java.sql.SQLException;
import java.util.List;

public interface IStudent {
	public boolean add(Student newStudent) throws SQLException;
	public boolean edit(Student updateStudent) throws SQLException;
	public boolean remove(int id) throws SQLException;
	public List<Student> view(String tableStudent) throws SQLException;
	public List<Student> fbn(String name) throws SQLException;
	public List<Student> fbid(int id) throws SQLException;
}
