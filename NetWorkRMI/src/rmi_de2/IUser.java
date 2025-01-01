package rmi_de2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUser extends Remote {
	public boolean checkUsername(String username) throws RemoteException;
	public boolean login(String username,String password) throws RemoteException;
}
