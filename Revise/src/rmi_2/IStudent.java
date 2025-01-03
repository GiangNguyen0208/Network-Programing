package rmi_2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IStudent extends Remote {
	public boolean add(Student newStudent) throws RemoteException;
	public boolean edit(Student editStudent) throws RemoteException;
	public boolean remove(int id) throws RemoteException;
	public List<Student> fbn(String name) throws RemoteException;
	public List<Student> view(String tableStudent) throws RemoteException;
	public List<Student> fbid(int id) throws RemoteException;
}
