package rmi_2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends UnicastRemoteObject implements IStudent {
	private static final long serialVersionUID = 1L;

	protected StudentDAO() throws RemoteException {
	}


	@Override
	public boolean add(Student newStudent) throws RemoteException {
		Connection connection = ConnectorUcanaccess.getConnection();
		String query = "Insert Into Student (ID, Name, BirthDay, Grade) Values (?,?,?,?)";
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
	public boolean edit(Student editStudent) throws RemoteException {
		Connection connection = ConnectorUcanaccess.getConnection();
		String query = "Update Student Set Name = ?, BirthDay = ?, Grade = ? Where ID = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, editStudent.getName());
			preparedStatement.setInt(2, editStudent.getBirthDay());
			preparedStatement.setDouble(3, editStudent.getGrade());
			preparedStatement.setInt(4, editStudent.getId());
			int rowAffected = preparedStatement.executeUpdate();
			return rowAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(int id) throws RemoteException {
		Connection connection = ConnectorUcanaccess.getConnection();
		String query = "Delete From Student Where ID = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int rowAffected = preparedStatement.executeUpdate();
			return rowAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Student> fbn(String name) throws RemoteException {
		List<Student> students = new ArrayList<>();
		Connection connection = ConnectorUcanaccess.getConnection();
		String query = "Select * From Student Where Name Like ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%"+name+"%");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String nameS = rs.getString("Name");
				int birthday = rs.getInt("BirthDay");
				double grade = rs.getDouble("Grade");
				students.add(new Student(id, nameS, birthday, grade));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public List<Student> view(String tableStudent) throws RemoteException {
		List<Student> students = new ArrayList<>();
		Connection connection = ConnectorUcanaccess.getConnection();
		String query = "Select * From " + tableStudent;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String nameS = rs.getString("Name");
				int birthday = rs.getInt("BirthDay");
				double grade = rs.getDouble("Grade");
				students.add(new Student(id, nameS, birthday, grade));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public List<Student> fbid(int id) throws RemoteException {
		List<Student> students = new ArrayList<>();
		Connection connection = ConnectorUcanaccess.getConnection();
		String query = "Select * From Student Where Name Like ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String nameS = rs.getString("Name");
				int birthday = rs.getInt("BirthDay");
				double grade = rs.getDouble("Grade");
				students.add(new Student(id, nameS, birthday, grade));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return students;
	}
	public static void main(String[] args) throws RemoteException {
		StudentDAO dao = new StudentDAO();
		System.out.println(dao.edit(new Student(9, "Nguyễn Lê Khang", 2005, 7.5)));
	}
	
}
