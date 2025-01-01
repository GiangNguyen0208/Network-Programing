package tcp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements IProductDAO {

	@Override
	public boolean add(Product newProduct) throws SQLException {
		boolean isAdd = false;
		List<Product> stock = view("products");
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "Insert into products (ID, Name, Price, Quantity) Values (?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, newProduct.getId());
		for (Product product : stock) {
			if (newProduct.getId() == product.getId()) {
				return isAdd = false;
			}
		}
		preparedStatement.setString(2, newProduct.getName());
		preparedStatement.setDouble(3, newProduct.getPrice());
		preparedStatement.setInt(4, newProduct.getQuantity());
		int rowAffected = preparedStatement.executeUpdate();
		return isAdd = rowAffected>0;
	}

	@Override
	public boolean update(Product updateProduct) throws SQLException {
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "Update products Set Name = ?, Price = ?, Quantity = ? Where ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, updateProduct.getName());
		preparedStatement.setDouble(2, updateProduct.getPrice());
		preparedStatement.setInt(3, updateProduct.getQuantity());
		preparedStatement.setInt(4, updateProduct.getId());
		int rowAffected = preparedStatement.executeUpdate();
		return rowAffected>0;
	}

	@Override
	public boolean remove(int id) throws SQLException {
		Connection connection = UCanAccessConnector.getConnection();
		String sql = "Delete From products Where ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		int rowAffected = preparedStatement.executeUpdate();
		return rowAffected>0;
	}

	@Override
	public List<Product> view(String tableProduct) throws SQLException {
		List<Product> products = new ArrayList<>();
		Connection connection = UCanAccessConnector.getConnection();
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
	
}
