import java.sql.Connection;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Successfully connected to MySQL.");
        } catch (SQLException e) {
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());

            e.printStackTrace();
        }
    }
}
