package rmi_2;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(6677);
		IUser userDAO = new UserDAO();
		IStudent studentDAO = new StudentDAO();
		registry.bind("userDAO", userDAO);
		registry.bind("studentDAO", studentDAO);
	}
}
