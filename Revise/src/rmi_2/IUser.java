package rmi_2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUser extends Remote {
	public boolean checkUsername(String username) throws RemoteException;
	public boolean checkLogin(String username, String password) throws RemoteException;
	public String createSessionID(String username) throws RemoteException;
	public boolean checkSessionID(String session) throws RemoteException;
	public void logout(String session) throws RemoteException;
}
