package rmi_de1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IStudentDAO extends Remote {
	public boolean add(Student newStudent) throws RemoteException;
	public boolean remove(int id) throws RemoteException;
	public boolean edit(Student newStudent) throws RemoteException;
	public List<Student> view(String nameStudent) throws RemoteException;
	
}
