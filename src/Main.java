import edu.aitu.oop3.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        CafeteriaApp app = new CafeteriaApp();
        app.run();
    }
}
