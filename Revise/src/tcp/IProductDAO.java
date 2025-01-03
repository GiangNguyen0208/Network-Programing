package tcp;

import java.sql.SQLException;
import java.util.List;

public interface IProductDAO {
	public boolean add(Product newProduct) throws SQLException;
	public boolean update(Product updateProduct) throws SQLException;
	public boolean remove(int id) throws SQLException;
	public List<Product> view(String tableProduct) throws SQLException;
}
