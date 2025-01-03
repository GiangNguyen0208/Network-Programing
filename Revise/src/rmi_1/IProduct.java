package rmi_1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IProduct extends Remote {
	public boolean add(Product newProduct) throws RemoteException;
	public boolean edit(Product editProduct) throws RemoteException;
	public boolean remove(int id) throws RemoteException;
	public List<Product> findByName(String name) throws RemoteException;
	public Product findByID(int id) throws RemoteException;
	public List<Product> view(String tableProducts) throws RemoteException;
}
