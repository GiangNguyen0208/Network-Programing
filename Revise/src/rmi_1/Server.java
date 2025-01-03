package rmi_1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Locale;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(7753);
		IUser userDAO = new UserDAO();
		IProduct productDAO = new ProductDAO();
		registry.bind("userDAO", userDAO);
		registry.bind("productDAO", productDAO);
	}
}
