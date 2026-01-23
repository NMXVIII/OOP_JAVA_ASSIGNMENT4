package entities;

import java.util.List;

public class Order {
    private Integer id;
    private int customerId;
    private String address;
    private boolean completed;
    private List<OrderItem> items;

    public Order(int customerId, String address, List<OrderItem> items) {
        this.customerId = customerId;
        this.address = address;
        this.items = items;
        this.completed = false;
    }

    public Order(int id, int customerId, String address,boolean completed, List<OrderItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.address = address;
        this.completed = completed;
        this.items = items;
    }

    public Integer getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getAddress () { return address; }
    public boolean isCompleted() { return completed; }
    public List<OrderItem> getItems() { return items; }

    public void setId(Integer id) { this.id = id; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public double getTotalPrice() {
        double total = 0;
       // for (OrderItem Item : items) total += items.getTotal();
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id =" + id +
                ", customerId =" + customerId +
                ", address='" + address +'\'' +
                "; completed =" + completed +
                ", totalPrice =" +  getTotalPrice() +
                '}';
    }


}
