package rmi_de1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		IUserDAO userDAO = new UserDAO();
		IStudentDAO studentDAO = new StudentDAO();
		Registry registry = LocateRegistry.createRegistry(3355);
		registry.bind("userDAO", userDAO);
		registry.bind("studentDAO", studentDAO);
	}
}
