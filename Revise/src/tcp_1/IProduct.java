package tcp_1;

import java.sql.SQLException;
import java.util.List;

public interface IProduct {
	public boolean add(Product newProduct) throws SQLException;
	public boolean edit(Product updateProduct) throws SQLException;
	public boolean remove(int id) throws SQLException;
	public List<Product> view(String tableProduct) throws SQLException;
	public List<Product> fbn(String name) throws SQLException;
	public List<Product> fbid(int id) throws SQLException;
}
