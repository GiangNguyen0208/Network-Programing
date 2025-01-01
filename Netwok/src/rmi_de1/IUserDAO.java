package rmi_de1;

import java.rmi.*;

public interface IUserDAO extends Remote {
	boolean checkUsername(String username) throws RemoteException;
	boolean login(String username, String password) throws RemoteException;
}
