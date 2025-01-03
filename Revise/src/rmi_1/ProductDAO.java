package rmi_1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends UnicastRemoteObject implements IProduct {

	protected ProductDAO() throws RemoteException {
	}

	@Override
	public boolean add(Product newProduct) throws RemoteException {
		Connection connection = Connector.getConnection();
		String query = "Insert Into products (ID, Name, Price, Quantity) Values (?,?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, newProduct.getId());
			preparedStatement.setString(2, newProduct.getName());
			preparedStatement.setDouble(3, newProduct.getPrice());
			preparedStatement.setInt(4, newProduct.getQuantity());
			int rowAffected = preparedStatement.executeUpdate();
			return rowAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean edit(Product editProduct) throws RemoteException {
		Connection connection = Connector.getConnection();
		String query = "Update products Set Name = ?, Price = ?, Quantity = ? Where ID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, editProduct.getName());
			preparedStatement.setDouble(2, editProduct.getPrice());
			preparedStatement.setInt(3, editProduct.getQuantity());
			preparedStatement.setInt(4, editProduct.getId());
			int rowAffected = preparedStatement.executeUpdate();
			return rowAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(int id) throws RemoteException {
		Connection connection = Connector.getConnection();
		String query = "Delete From products Where ID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			int rowAffected = preparedStatement.executeUpdate();
			return rowAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Product> findByName(String name) throws RemoteException {
		List<Product> products = new ArrayList<>();
		Connection connection = Connector.getConnection();
		String query = "Select * From products Where name Like ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%" + name + "%");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String nameP = rs.getString("Name");
				double price = rs.getDouble("Price");
				int quantity = rs.getInt("Quantity");
				products.add(new Product(id, nameP, price, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Product findByID(int id) throws RemoteException {
		Product product = new Product();
		Connection connection = Connector.getConnection();
		String query = "Select * From products Where ID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String nameP = rs.getString("Name");
				double price = rs.getDouble("Price");
				int quantity = rs.getInt("Quantity");
				product = new Product(id, nameP, price, quantity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public List<Product> view(String tableProducts) throws RemoteException {
		List<Product> products = new ArrayList<>();
		Connection connection = Connector.getConnection();
		String query = "Select * From " + tableProducts;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String nameP = rs.getString("Name");
				double price = rs.getDouble("Price");
				int quantity = rs.getInt("Quantity");
				products.add(new Product(id, nameP, price, quantity));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

}
