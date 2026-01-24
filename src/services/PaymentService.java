package services;

import entities.Order;
import exceptions.OrderNotFoundException;

public class PaymentService {

    public void processPayment(Order order) throws OrderNotFoundException {

        if (order == null) {
            throw new OrderNotFoundException("Order not found for payment");
        }

        if (order.isCompleted()) {
            System.out.println("⚠ Order is already completed.");
            return;
        }

        double total = calculateTotal(order);

        System.out.println("💳 Processing payment...");
        System.out.printf("💰 Total amount: $%.2f%n", total);
        System.out.println("✅ Payment successful!");

        order.setCompleted(true);
    }

    private double calculateTotal(Order order) {
        return order.getItems()
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
