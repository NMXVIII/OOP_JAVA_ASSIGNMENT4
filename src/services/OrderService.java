package services;

import entities.Order;
import entities.OrderItem;
import entities.MenuItem;
import exceptions.InvalidQuantityException;
import exceptions.MenuItemNotAvailableException;
import exceptions.OrderNotFoundException;
import repositories.IMenuItemRepository;
import java.sql.SQLException;
import java.util.*;

public class OrderService {
    private final IMenuItemRepository menuRepo;
    private final Map<Integer, Order> activeOrders = new HashMap<>();
    private int nextOrderId = 1;

    public OrderService(IMenuItemRepository menuRepo) {
        this.menuRepo = menuRepo;
    }

    public Order placeOrder(int customerId, List<OrderItem> items)
            throws SQLException, InvalidQuantityException, MenuItemNotAvailableException {

        for (OrderItem orderItem : items) {
            MenuItem menuItem = menuRepo.findById(orderItem.getMenuItemId());

            if (menuItem == null) {
                throw new MenuItemNotAvailableException("Menu item with ID " + orderItem.getMenuItemId() + " does not exist.");
            }

            if (menuItem.getQuantity() < orderItem.getQuantity()) {
                throw new MenuItemNotAvailableException("Not enough quantity for " + menuItem.getName());
            }
            orderItem.setMenuItemName(menuItem.getName());
            orderItem.setPrice(menuItem.getPrice());
        }
        Order order = new Order(customerId);
        order.setId(nextOrderId++);
        order.setItems(items);
        activeOrders.put(order.getId(), order);
        return order;
    }

    public List<Order> getActiveOrders() {
        List<Order> orders = new ArrayList<>();
        for (Order order : activeOrders.values()) {
            if (!order.isCompleted()) {
                orders.add(order);
            }
        }
        return orders;
    }

    public void markOrderAsCompleted(int orderId) throws OrderNotFoundException {
        Order order = activeOrders.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }
        order.setCompleted(true);
    }

    public Order getOrderById(int orderId) throws OrderNotFoundException {
        Order order = activeOrders.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found");
        }
        return order;
    }

    public void validateStock(int itemId, int requestedQty) throws MenuItemNotAvailableException, SQLException {
        MenuItem item = menuRepo.findById(itemId);
        if (item == null) {
            throw new MenuItemNotAvailableException("Item ID " + itemId + " does not exist.");
        }
        if (item.getQuantity() < requestedQty) {
            throw new InvalidQuantityException("Insufficient stock. Available: " + item.getQuantity());
        }
    }
}
