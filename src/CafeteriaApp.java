import interfaces.IDB;
import edu.aitu.oop3.db.DatabaseConnection;
import entities.MenuItem;
import entities.Order;
import entities.OrderItem;
import repositories.MenuItemRepository;
import services.MenuService;
import services.OrderService;
import exceptions.*;
import services.PaymentService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CafeteriaApp {
    private MenuItemRepository repo;
    private MenuService menuService;
    private OrderService orderService;
    private Scanner scanner;
    private PaymentService paymentService;


    public CafeteriaApp() {
        IDB db = (IDB) DatabaseConnection.getInstance();
        this.repo = new MenuItemRepository(db);
        this.menuService = new MenuService(repo);
        this.orderService = new OrderService(repo);
        this.scanner = new Scanner(System.in);
        this.paymentService = new PaymentService();
    }

    public void run() throws SQLException {
        System.out.println("Welcome to Cafeteria!");

        while (true) {
            System.out.println("\n1. Add Menu item");
            System.out.println("2. Find by ID");
            System.out.println("3. Place an order");
            System.out.println("4. View active orders");
            System.out.println("5. Mark order as completed");
            System.out.println("6. View menu");
            System.out.println("7. Pay for order");
            System.out.print("8. Quit");
            System.out.print("\nChoose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMenuItem();
                    break;
                case 2:
                    findMenuItem();
                    break;
                case 3:
                    placeOrder();
                    break;
                case 4:
                    viewActiveOrders();
                    break;
                case 5:
                    markOrderCompleted();
                    break;
                case 6:
                    viewMenu();
                    break;
                case 7:
                    payForOrder();
                    break;
                case 8:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addMenuItem() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            if (name == null || name.trim().isEmpty()) {
                System.out.println("Error: Name cannot be empty!");
                return;
            }

            System.out.print("Description: ");
            String desc = scanner.nextLine();

            System.out.print("Price: ");
            Double price = scanner.nextDouble();

            if (price < 0) {
                System.out.println("Error: Price cannot be negative!");
                scanner.nextLine();
                return;
            }

            System.out.print("Quantity: ");
            Integer qty = scanner.nextInt();
            scanner.nextLine();

            if (qty < 0) {
                System.out.println("Error: Quantity cannot be negative!");
                return;
            }

            MenuItem item = new MenuItem(name, desc, price, qty);
            menuService.addMenuItem(item);
            System.out.println("Item added!");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter valid numbers for price and quantity! ");
            scanner.nextLine();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void findMenuItem() {
        try {
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            MenuItem item = repo.findById(id);
            System.out.println(item);

        } catch (SQLException e) {
            System.out.println("Error: Item not found with that ID!");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter a valid number!");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private void placeOrder()  {
            System.out.print("Enter customer ID: ");
            int customerId = getIntInput();
            List<OrderItem> orderItems = new ArrayList<>();

            while (true) {
                try {
                    menuService.getAllAvailableItems().forEach(System.out::println);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("\nEnter menu item ID (0 to finish): ");
                int menuItemId = getIntInput();
                if (menuItemId == 0) break;
                System.out.print("Enter quantity: ");
                int quantity = getIntInput();
                orderItems.add(new OrderItem(0, menuItemId, null, quantity, 0.0));
            }
            try{
            Order order = orderService.placeOrder(customerId, orderItems);
            System.out.println("Order placed successfully! Order ID: " + order.getId());
            } catch (MenuItemNotAvailableException | InvalidQuantityException e) {
            System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewActiveOrders() {
        try {
            List<Order> orders = orderService.getActiveOrders();

            if (orders.isEmpty()) {
                System.out.println("No active orders.");
                return;
            }

            System.out.println("\n=== ACTIVE ORDERS ===");
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getId() +
                        " | Customer ID: " + order.getCustomerId() +
                        " | Completed: " + order.isCompleted());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void markOrderCompleted() {
        try {
            System.out.print("Enter order ID to complete: ");
            int orderId = getIntInput();

            orderService.markOrderAsCompleted(orderId);
            System.out.println("Order marked as completed!");

        } catch (OrderNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewMenu() {
        try {
            List<MenuItem> items = menuService.getAllAvailableItems();

            System.out.println("\n=== MENU ===");
            for (MenuItem item : items) {
                System.out.printf("ID: %d | %s - $%.2f%n",
                        item.getId(), item.getName(), item.getPrice());
                System.out.println("   " + item.getDescription());
                System.out.println("   Available: " + item.getQuantity());
                System.out.println("---");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a number: ");
                scanner.nextLine();
            }
        }
    }

    public MenuItemRepository getRepo() {
        return repo;
    }

    public void setRepo(MenuItemRepository repo) {
        this.repo = repo;
    }

    private void payForOrder() {
        try {
            System.out.print("Enter order ID to pay: ");
            int orderId = getIntInput();

            Order order = orderService.getOrderById(orderId);
            paymentService.processPayment(order);

            System.out.println("Order paid and completed!");

        } catch (OrderNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }
}