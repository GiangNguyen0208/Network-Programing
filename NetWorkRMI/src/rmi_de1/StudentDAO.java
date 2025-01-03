package rmi_de1;

import java.rmi.RemoteException;
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
	public boolean add(Student newStudent) throws RemoteException {
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "INSERT INTO Student (ID, Name, BirthDay, Grade) VALUES (?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, newStudent.getId());
			preparedStatement.setString(2, newStudent.getName());
			preparedStatement.setInt(3, newStudent.getbYear());
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
	public boolean remove(int id) throws RemoteException {
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "Delete From Student Where ID = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			int rowAffected = preparedStatement.executeUpdate();
			if (rowAffected > 0)
				return true;
		} catch (SQLException e) {
			throw new RemoteException("ERROR");
		}
		return false;
	}

	@Override
	public boolean edit(Student editStudent) throws RemoteException {
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "UPDATE Student SET Name =?, BirthDay =?, Grade =? WHERE ID =?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, editStudent.getName());
			preparedStatement.setInt(2, editStudent.getbYear());
			preparedStatement.setDouble(3, editStudent.getGrade());
			preparedStatement.setInt(4, editStudent.getId());
			int rowAffected = preparedStatement.executeUpdate();
			if (rowAffected > 0)
				return true;
		} catch (SQLException e) {
			throw new RemoteException("ERROR");
		}
		return false;
	}

	@Override
	public List<Student> view(String nameStudent) throws RemoteException {
		List<Student> students = new ArrayList<Student>();
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "SELECT * FROM Student WHERE Name Like ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "%" + nameStudent + "%");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				students.add(new Student(rs.getInt("ID"), rs.getString("Name"), rs.getInt("BirthDay"), rs.getDouble("Grade")));
			}
		} catch (SQLException e) {
			throw new RemoteException("ERROR");
		}
		return students;
	}
	public static void main(String[] args) throws RemoteException {
		IStudentDAO dao = new StudentDAO();
		List<Student> students = dao.view("Giang");
		dao.view("Giang").forEach(System.out::println);
		System.out.println("EDIT" );
		dao.edit(new Student(1, "Trương Nguyễn Hương Giang", 2002, 10.0));
		dao.view("Giang").forEach(System.out::println);
		System.out.println("ADD" );
		dao.add(new Student(10, "Nguyễn Trường Giang", 2003, 8.5));
		dao.view("Giang").forEach(System.out::println);
		
	}
}
