package tcp_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProduct{

	@Override
	public boolean add(Product newProduct) throws SQLException {
		Connection connection = ConnectUCanaccess.getConnection();
		String sql = "Insert Into products (ID, Name, Price, Quantity) Values (?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, newProduct.getId());
		preparedStatement.setString(2, newProduct.getName());
		preparedStatement.setDouble(3, newProduct.getPrice());
		preparedStatement.setInt(4, newProduct.getQuantity());
		int rowAffected = preparedStatement.executeUpdate();
		return rowAffected > 0;
	}

	@Override
	public boolean edit(Product updateProduct) throws SQLException {
		Connection connection = ConnectUCanaccess.getConnection();
		String sql = "Update products Set Name, Price, Quantity Where ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, updateProduct.getName());
		preparedStatement.setDouble(2, updateProduct.getPrice());
		preparedStatement.setInt(3, updateProduct.getQuantity());
		preparedStatement.setInt(4, updateProduct.getId());
		int rowAffected = preparedStatement.executeUpdate();
		return rowAffected > 0;
	}

	@Override
	public boolean remove(int id) throws SQLException {
		Connection connection = ConnectUCanaccess.getConnection();
		String sql = "Delete From products Where ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		int rowAffected = preparedStatement.executeUpdate();
		return rowAffected > 0;
	}

	@Override
	public List<Product> view(String tableProduct) throws SQLException {
		List<Product> products = new ArrayList<>();
		Connection connection = ConnectUCanaccess.getConnection();
		String sql = "Select * From " + tableProduct;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
			String name = rs.getString("Name");
			double price = rs.getDouble("Price");
			int quantity = rs.getInt("Quantity");
			products.add(new Product(id, name, price, quantity));
		}
		return products;
	}

	@Override
	public List<Product> fbn(String name) throws SQLException {
		List<Product> products = new ArrayList<>();
		Connection connection = ConnectUCanaccess.getConnection();
		String sql = "Select * From products Where Name Like ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, "%"+name+"%");
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("ID");
			String nameP = rs.getString("Name");
			double price = rs.getDouble("Price");
			int quantity = rs.getInt("Quantity");
			products.add(new Product(id, nameP, price, quantity));
		}
		return products;
	}

	@Override
	public List<Product> fbid(int id) throws SQLException {
		List<Product> products = new ArrayList<>();
		Connection connection = ConnectUCanaccess.getConnection();
		String sql = "Select * From products Where ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			int idP = rs.getInt("ID");
			String nameP = rs.getString("Name");
			double price = rs.getDouble("Price");
			int quantity = rs.getInt("Quantity");
			products.add(new Product(idP, nameP, price, quantity));
		}
		return products;
	}
	public static void main(String[] args) throws SQLException {
		ProductDAO dao = new ProductDAO();
		System.out.println(dao.view("products"));
	}
}
