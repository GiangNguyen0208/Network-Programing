package rmi_de1;

import java.rmi.*;
import java.util.List;

public interface IStudentDAO extends Remote {
	boolean add(Student student) throws RemoteException;
	boolean remove(int id) throws RemoteException;
	boolean edit(Student studentUpdate) throws RemoteException;
	List<Student> view(String name)  throws RemoteException;
}
