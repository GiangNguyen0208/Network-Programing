package rmi_de2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IStudent extends Remote {
	public boolean add(Student newStudent) throws RemoteException;
	public boolean edit(Student updateStudent) throws RemoteException;
	public boolean remove(int id) throws RemoteException;
	public List<Student> viewByName(String name) throws RemoteException;
	public List<Student> view(String table) throws RemoteException;
}
