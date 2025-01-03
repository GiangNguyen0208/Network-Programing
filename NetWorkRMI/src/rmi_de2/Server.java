package rmi_de2;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {
		IUser userDAO = new UserDAO();
		IStudent studentDAO = new StudentDAO();
		Registry req = LocateRegistry.createRegistry(9630);
		req.bind("userDAO", userDAO);
		req.bind("studentDAO", studentDAO);
	}
}
