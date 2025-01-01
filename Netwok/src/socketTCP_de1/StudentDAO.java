package socketTCP_de1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudentDAO  {

	@Override
	public List<Student> findStudentByID(int id) throws SQLException {
		Connection conn = UCanAccessConnector.getConnection();
		List<Student> students = new ArrayList<>();
		String sql = "SELECT * FROM Student WHERE id = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet rs = preparedStatement.executeQuery();
		
		if (!rs.isBeforeFirst()) {
			System.out.println("Không tìm thấy sinh viên nào với tên: " + id);
            return null;
		}
		
		System.out.println("Thông tin sinh viên id: " + id);
		while (rs.next()) {
			students.add(new Student(rs.getInt("ID"), rs.getString("Name"), rs.getInt("BirthDay"), rs.getDouble("Grade")));
		}
		return students;
	}

	@Override
	public List<Student> findStudentByName(String name) throws SQLException {
		Connection conn = UCanAccessConnector.getConnection();
		List<Student> students = new ArrayList<>();
		String sql = "SELECT * FROM Student WHERE Name LIKE ?";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, "%" + name + "%");
		ResultSet rs = preparedStatement.executeQuery();
		
		if (!rs.isBeforeFirst()) {
			 System.out.println("Không tìm thấy sinh viên nào với tên: " + name);
             return students;
		}
		
		System.out.println("Thông tin sinh viên tên: " + name);
		while (rs.next()) {
			students.add(new Student(rs.getInt("ID"), rs.getString("Name"), rs.getInt("BirthDay"), rs.getDouble("Grade")));
		}
		return students;
	}
	@Override
	public boolean createNewStudent(Student student) throws SQLException {
		boolean isCreated = false;
		Connection conn = UCanAccessConnector.getConnection();
		String sql = "INSERT INTO Student (ID, Name, BirthDay, Grade) VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, student.getId());
		preparedStatement.setString(2, student.getName());
		preparedStatement.setInt(3, student.getYear());
		preparedStatement.setDouble(4, student.getGrade());
		int rowAffected = preparedStatement.executeUpdate();
		
		if (rowAffected > 0) {
			isCreated = true;
		} 
		return isCreated;
	}
	@Override
	public List<Student> view(String table) throws SQLException {
		List<Student> students = new ArrayList<>();

	    Connection conn = UCanAccessConnector.getConnection();
	    String sql = "SELECT * FROM " + table;
	    PreparedStatement preparedStatement = conn.prepareStatement(sql);
	    
	    ResultSet rs = preparedStatement.executeQuery();
	    while (rs.next()) {
	    	students.add(new Student(
	                rs.getInt("ID"),
	                rs.getString("Name"),
	                rs.getInt("BirthDay"),
	                rs.getDouble("Grade"))
	            );
	    }
	    return students;
	    
	}
	
	public static void main(String[] args) throws SQLException {
		IStudentDAO studentDAO = new StudentDAO();
//		List<Student> students = studentDAO.findStudentByID(1);
//		for (Student student : students) {
//			System.out.println(student.toString());
//		}
//		List<Student> students2 = studentDAO.findStudentByName("Giang");
//		for (Student student : students2) {
//			System.out.println(student.toString());
//		}
		Student student = new Student(6, "Nguyễn Cao Giang", 2001, 7.9);
		boolean isCreated = studentDAO.createNewStudent(student);
		if (isCreated) {
			student.toString();
		} else {
			System.out.println("Created Fail.! :)");
		}
	}



}
