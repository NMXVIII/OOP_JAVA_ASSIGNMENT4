package services;

import entities.MenuItem;
import exceptions.MenuItemNotAvailableException;
import repositories.IMenuItemRepository;

import java.sql.SQLException;
import java.util.List;

public class MenuService {
    private IMenuItemRepository menuRepo;

    public MenuService(IMenuItemRepository menuItemRepository){
        this.menuRepo = menuItemRepository;
    }

    public List<MenuItem> getAllAvailableItems() throws SQLException {
        return menuRepo.findAll()
                .stream()
                .filter(item -> item.getQuantity() > 0)
                .toList();
    }

    public MenuItem getMenuItemById(int id) throws SQLException, MenuItemNotAvailableException{
        MenuItem item = menuRepo.findById(id);
        if(item.getQuantity() <= 0 ) {
            throw new MenuItemNotAvailableException("Menu item '" + item.getName() + "' is not available");
        }
        return item;
    }
    public void addMenuItem(MenuItem item) throws SQLException {
        menuRepo.save(item);
    }
}
