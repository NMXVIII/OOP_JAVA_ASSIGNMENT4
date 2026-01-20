package repositories;

import interfaces.IDB;
import entities.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemRepository implements IMenuItemRepository {
    private IDB database;

    public MenuItemRepository(IDB database) {
        this.database = database;
    }

    @Override
    public void save(MenuItem item) throws SQLException {
        String sql = "INSERT INTO menu_items (name, description, price, quantity) VALUES (?, ?, ?, ?)";
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, item.getName());
        stmt.setString(2, item.getDescription());
        stmt.setDouble(3, item.getPrice());
        stmt.setInt(4, item.getQuantity());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Override
    public List<MenuItem> findAll() throws SQLException {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";

        Connection conn = database.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            MenuItem item = new MenuItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
            );
            items.add(item);
        }

        rs.close();
        stmt.close();
        conn.close();
        return items;
    }

    @Override
    public MenuItem findById(int id) throws SQLException {
        String sql = "SELECT * FROM menu_items WHERE id = ?";
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            MenuItem item = new MenuItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")
            );
            rs.close();
            stmt.close();
            conn.close();
            return item;
        }

        throw new SQLException("Item not found");
    }
}
