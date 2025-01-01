import java.sql.*;

public class ConnectDAO {
    static Connection conn;
    ConnectDAO() throws ClassNotFoundException, SQLException {
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        Class.forName(driver);
        String accessFile = "C:\\Users\\COHOTECH.VN\\Documents\\Student.accdb";
        String url = "jdbc:ucanaccess://" + accessFile;
        this.conn = DriverManager.getConnection(url);
    }

    public static void main(String[] args) throws SQLException {
        int id = 1;
        findStudentByID(id);
    }
    public static void findStudentByID(int id) throws SQLException {
        String sql = "SELECT * From Student Where ID = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            System.out.println("ID: " + result.getInt("ID"));
            System.out.println("Name: " + result.getInt("Name"));
            System.out.println("BirthDay: " + result.getInt("BirthDay"));
            System.out.println("Grade: " + result.getInt("Grade"));
        }
    }
}
