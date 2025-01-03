package rmi_de2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends UnicastRemoteObject implements IStudent {

	protected StudentDAO() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean add(Student newStudent) throws RemoteException {
		Connection connection = UCanAccessConnector.getConnection();
		String query = "Inseart into Student (ID, Name, BirthDay, Grade) Values (?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, newStudent.getId());
			preparedStatement.setString(2, newStudent.getName());
			preparedStatement.setInt(3, newStudent.getBirthDay());
			preparedStatement.setDouble(4, newStudent.getGrade());
			int rowAffected = preparedStatement.executeUpdate();
			return rowAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean edit(Student updateStudent) throws RemoteException {
		Connection connection = UCanAccessConnector.getConnection();
		String query = "Update Student Set Name = ?, BirthDat = ?, Grade = ? Where ID = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, updateStudent.getName());
			preparedStatement.setInt(2, updateStudent.getBirthDay());
			preparedStatement.setDouble(3, updateStudent.getGrade());
			preparedStatement.setInt(4, updateStudent.getId());
			int rowAffected = preparedStatement.executeUpdate();
			return rowAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(int id) throws RemoteException {
		Connection connection = UCanAccessConnector.getConnection();
		String query = "Delete From Student Where ID = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int rowAffected = preparedStatement.executeUpdate();
			return rowAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Student> viewByName(String name) throws RemoteException {
		List<Student> lists = new ArrayList<Student>();
		Connection connection = UCanAccessConnector.getConnection();
		String query = "Select * From Student Where Name Like ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%"+name+"%");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				lists.add(new Student(rs.getInt("ID"), rs.getString("Name"), rs.getInt("BirthDay"), rs.getDouble("Grade")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lists;
	}

	@Override
	public List<Student> view(String table) throws RemoteException {
		List<Student> lists = new ArrayList<Student>();
		Connection connection = UCanAccessConnector.getConnection();
		String query = "Select * From " + table;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				lists.add(new Student(rs.getInt("ID"), rs.getString("Name"), rs.getInt("BirthDay"), rs.getDouble("Grade")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lists;
	}
	public static void main(String[] args) throws RemoteException {
		StudentDAO dao = new StudentDAO();
		List<Student> students = dao.view("Student");
		for (Student student : students) {
			System.out.println(student.toString());
		}
	}
}
