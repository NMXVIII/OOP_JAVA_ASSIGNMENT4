import interfaces.IDB;
import edu.aitu.oop3.db.DatabaseConnection;
import entities.MenuItem;
import repositories.MenuItemRepository;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CafeteriaApp {
    private MenuItemRepository repo;
    private Scanner scanner;

    public CafeteriaApp() {
        IDB db = (IDB) DatabaseConnection.getInstance();
        repo = new MenuItemRepository(db);
        scanner = new Scanner(System.in);
    }

    public void run(){
        System.out.println("Welcome to Cafeteria!");

        while(true) {
            System.out.println("\n1. Add Menu item");
            System.out.println("2. Show all Menu items");
            System.out.println("3. Find by ID");
            System.out.print("4. Quit");
            System.out.print("\nChoose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMenuItem();
                    break;
                case 2:
                    showAllMenuItems();
                        break;
                case 3:
                    findMenuItem();
                        break;
                case 4:
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

            if(price < 0) {
                 System.out.println("Error: Price cannot be negative!");
                 scanner.nextLine();
                  return;
             }

             System.out.print("Quantity: ");
             Integer qty = scanner.nextInt();
             scanner.nextLine();

             if ( qty < 0) {
                 System.out.println("Error: Quantity cannot be negative!");
                  return;
             }

             MenuItem item = new MenuItem(name, desc, price, qty);
             repo.save(item);
              System.out.println("Item added!");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: Please enter valid numbers for price and quantity! ");
            scanner.nextLine();
        }  catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scanner.nextLine();
        }
    }


    private void showAllMenuItems() {
        try {
            List<MenuItem> items = repo.findAll();
            for (MenuItem item : items) {
                System.out.println(item);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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

    public MenuItemRepository getRepo() {
        return repo;
    }

    public void setRepo(MenuItemRepository repo) {
        this.repo = repo;
    }


}
