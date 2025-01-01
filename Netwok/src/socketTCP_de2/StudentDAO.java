package socketTCP_de2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudentDAO {

	@Override
	public List<Student> findStudentByID(int id) throws SQLException {
		 Connection conn = UcanAccessConnector.getConnection();
		 List<Student> lists = new ArrayList<>();
		 String sql = "Select * From Student Where ID = ?";
		 PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 preparedStatement.setInt(1, id);
		 ResultSet rs = preparedStatement.executeQuery();
		 
		 while (rs.next()) {
			 lists.add(new Student(rs.getInt("ID"), rs.getString("Name"), rs.getInt("BirthDay"), rs.getDouble("Grade")));
		 }
		 return lists;
	}

	@Override
	public List<Student> findStudentByName(String name) throws SQLException {
		 Connection conn = UcanAccessConnector.getConnection();
		 List<Student> lists = new ArrayList<>();
		 String sql = "Select * From Student Where Name Like ?";
		 PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 preparedStatement.setString(1, "%"+name+"%");
		 ResultSet rs = preparedStatement.executeQuery();
		 
		 while (rs.next()) {
			 lists.add(new Student(rs.getInt("ID"), rs.getString("Name"), rs.getInt("BirthDay"), rs.getDouble("Grade")));
		 }
		 return lists;
	}

	@Override
	public boolean add(Student student) throws SQLException {
		boolean isChange = false;
		Connection conn = UcanAccessConnector.getConnection();
		 String sql = "Select * From Student Where Name = ?";
		 PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 int rowAffected = preparedStatement.executeUpdate(sql);
		 if (rowAffected != 0) {
			 isChange = true; 
		 }
		 return isChange;
	}

	@Override
	public List<Student> viewAllStudents(String tablename) throws SQLException {
		Connection conn = UcanAccessConnector.getConnection();
		 List<Student> lists = new ArrayList<>();
		 String sql = "Select * From " + tablename;
		 PreparedStatement preparedStatement = conn.prepareStatement(sql);
		 ResultSet rs = preparedStatement.executeQuery();
		 while (rs.next()) {
			 lists.add(new Student(rs.getInt("ID"), rs.getString("Name"), rs.getInt("BirthDay"), rs.getDouble("Grade")));
		 }
		 return lists;
	}
}
