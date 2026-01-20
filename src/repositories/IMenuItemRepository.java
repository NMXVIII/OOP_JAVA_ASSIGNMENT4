package repositories;

import entities.MenuItem;
import java.sql.SQLException;
import java.util.List;

public interface IMenuItemRepository {
    void save(MenuItem item) throws  SQLException;
    List<MenuItem> findALL() throws SQLException;
    MenuItem findById(int id) throws  SQLException;
}
