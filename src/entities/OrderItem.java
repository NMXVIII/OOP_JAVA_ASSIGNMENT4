package entities;

public class OrderItem {
    private Integer id;
    private int orderId;
    private int menuItemId;
    private String menuItemName ;
    private int quantity;
    private double price;

    public OrderItem(int orderId, int menuItemId, String menuItemName, int quantity , double price){
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getId() { return id; }
    public int getMenuItemId() {
        return menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setMenuItemName(String name) { this.menuItemName = name; }
    public void setPrice(double price) { this.price = price; }

}
