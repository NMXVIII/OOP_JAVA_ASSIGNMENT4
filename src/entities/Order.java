package entities;

import java.util.List;

public class Order {

    private int id;
    private int customerId;
    private String status;
    private boolean completed;
    private List<OrderItem> items;

    public Order(int customerId) {
        this.customerId = customerId;
        this.completed = false;
        this.status = "NEW";
    }

    public Order(int id, String status, List<OrderItem> items) {
        this.id = id;
        this.status = status;
        this.items = items;
    }

    public Order(int id, int customerId, String status, boolean completed, List<OrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.completed = completed;
        this.items = items;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public boolean isCompleted() { return completed; }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
