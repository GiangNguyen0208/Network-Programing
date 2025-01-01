package rmi_de1;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends UnicastRemoteObject implements IStudentDAO {


	protected StudentDAO() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean add(Student student) throws RemoteException {
		boolean isAdded = false;
		Connection conn = JDBCConnector.getConnection();
		String sql = "INSERT INTO Student (ID, Name, BirthDay, Grade) VALUES (?,?,?,?)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, student.getName());
			preparedStatement.setInt(2, student.getYear());
			preparedStatement.setDouble(3, student.getGrade());
			preparedStatement.setInt(4, student.getId());
			int rowAfftected = preparedStatement.executeUpdate();
			if (rowAfftected>0) {
				isAdded = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}

	@Override
	public boolean remove(int id) throws RemoteException {
		boolean isDeleted = false;
		Connection conn = JDBCConnector.getConnection();
		String sql = "Delete From Student Where ID = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			int rowAffected = preparedStatement.executeUpdate();
			if (rowAffected > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDeleted;
	}

	@Override
	public boolean edit(Student studentUpdate) throws RemoteException {
		boolean isUpdate = false;
		Connection conn = JDBCConnector.getConnection();
		String sql = "Update Student Set Name =?, BirthDay =?, Grade =? Where ID = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, studentUpdate.getName());
			preparedStatement.setInt(2, studentUpdate.getYear());
			preparedStatement.setDouble(3, studentUpdate.getGrade());
			preparedStatement.setInt(4, studentUpdate.getId());
			int rowAffected = preparedStatement.executeUpdate();
			if (rowAffected>0) {
				isUpdate = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdate;
	}

	@Override
	public List<Student> view(String tableName) throws RemoteException {
		List<Student> students = new ArrayList<Student>();
		Connection conn = JDBCConnector.getConnection();
		String sql = "Select * From ? ";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, tableName);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String fname = rs.getString("Name");
				int year = rs.getInt("BirthDay");
				double grade = rs.getDouble("Grade");
				students.add(new Student(id, fname, year, grade));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}
	public static void main(String[] args) throws RemoteException {
		IStudentDAO studentDAO = new StudentDAO();
		List<Student> students = studentDAO.view("Student");
		for (Student s : students) {
			System.out.println(s.toString());
		}
	}
	
}
