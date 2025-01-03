package tcp_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudent{

	@Override
	public boolean add(Student newStudent) throws SQLException {
		Connection conn = UCanAccessConnector.getConnection();
		String query = "Insert into Student (ID, Name, BirthDay, Grade) values (?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, newStudent.getId());
		preparedStatement.setString(2, newStudent.getName());
		preparedStatement.setInt(3, newStudent.getBirthDay());
		preparedStatement.setDouble(4, newStudent.getGrade());
		int rowAffected = preparedStatement.executeUpdate();
		return rowAffected > 0;
	}

	@Override
	public boolean edit(Student updateStudent) throws SQLException {
		Connection conn = UCanAccessConnector.getConnection();
		String query = "Update Student Set Name, BirthDay, Grade Where ID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(4, updateStudent.getId());
		preparedStatement.setString(1, updateStudent.getName());
		preparedStatement.setInt(2, updateStudent.getBirthDay());
		preparedStatement.setDouble(3, updateStudent.getGrade());
		int rowAffected = preparedStatement.executeUpdate();
		return rowAffected > 0;
	}

	@Override
	public boolean remove(int id) throws SQLException {
		Connection conn = UCanAccessConnector.getConnection();
		String query = "Delete From Student Where ID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, id);
		int rowAffected = preparedStatement.executeUpdate();
		return rowAffected > 0;
	}

	@Override
	public List<Student> view(String tableStudent) throws SQLException {
		List<Student> students = new ArrayList<>();
		Connection conn = UCanAccessConnector.getConnection();
		String query = "Select * From " + tableStudent;
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
			String name = rs.getString("Name");
			int year = rs.getInt("BirthDay");
			double grade = rs.getDouble("Grade");
			students.add(new Student(id, name, year, grade));
		}
		return students;
	}

	@Override
	public List<Student> fbn(String name) throws SQLException {
		List<Student> students = new ArrayList<>();
		Connection conn = UCanAccessConnector.getConnection();
		String query = "Select * From Student Where Name = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, "%"+name+"%");
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
			String nameS = rs.getString("Name");
			int year = rs.getInt("BirthDay");
			double grade = rs.getDouble("Grade");
			students.add(new Student(id, nameS, year, grade));
		}
		return students;
	}

	@Override
	public List<Student> fbid(int id) throws SQLException {
		List<Student> students = new ArrayList<>();
		Connection conn = UCanAccessConnector.getConnection();
		String query = "Select * From Student Where ID = ?";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, id);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int idS = rs.getInt("ID");
			String nameS = rs.getString("Name");
			int year = rs.getInt("BirthDay");
			double grade = rs.getDouble("Grade");
			students.add(new Student(idS, nameS, year, grade));
		}
		return students;
	}
	public static void main(String[] args) {
	}
}
