package rmi_1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IUser extends Remote {
	public boolean checkUsername(String username) throws RemoteException;
	public boolean checkLogin(String username, String passwords) throws RemoteException;
	public String createSession(String username) throws RemoteException;
	public boolean isValidSession(String session) throws RemoteException;
	public void logout(String session) throws RemoteException;
}
