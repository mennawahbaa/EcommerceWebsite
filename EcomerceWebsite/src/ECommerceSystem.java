
// Main class for demonstration

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Cart.CartItem;
import Customer.Customer;
import Product.ExpirableProduct;
import Product.ExpirableShippableProduct;
import Product.Product;
import Product.ProductFactory;

public class ECommerceSystem {
    public static void main(String[] args) {
        System.out.println("=== E-COMMERCE SYSTEM DEMO ===\n");

        // Create products
        Product cheese = ProductFactory.createProduct("expirable-shippable", "Cheese", 8.99, 20, 
                                                     LocalDate.of(2025, 8, 1), 0.5);
        Product biscuits = ProductFactory.createProduct("expirable", "Biscuits", 3.49, 50, 
                                                       LocalDate.of(2025, 7, 20), 0.0);
        Product tv = ProductFactory.createProduct("shippable", "TV", 499.99, 10, 
                                                 null, 15.0);
        Product mobile = ProductFactory.createProduct("simple", "Mobile", 699.99, 25, 
                                                     null, 0.0);
        Product scratchCard = ProductFactory.createProduct("simple", "Mobile Scratch Card", 10.00, 100, 
                                                          null, 0.0);

        // Create customer
        Customer customer = new Customer("John Doe", 1000.00);

        // Display available products
        System.out.println("Available Products:");
        System.out.printf("- %s: $%.2f (%d available", cheese.getName(), cheese.getPrice(), cheese.getQuantity());
        if (cheese.isExpirable()) {
            ExpirableShippableProduct expCheese = (ExpirableShippableProduct) cheese;
            System.out.printf(", expires: %s", expCheese.getExpirationDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
        }
        if (cheese.isShippable()) {
            System.out.printf(", weight: %.1fkg", cheese.getWeight());
        }
        System.out.println(")");

        System.out.printf("- %s: $%.2f (%d available", biscuits.getName(), biscuits.getPrice(), biscuits.getQuantity());
        if (biscuits.isExpirable()) {
            ExpirableProduct expBiscuits = (ExpirableProduct) biscuits;
            System.out.printf(", expires: %s", expBiscuits.getExpirationDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
        }
        System.out.println(")");

        System.out.printf("- %s: $%.2f (%d available", tv.getName(), tv.getPrice(), tv.getQuantity());
        if (tv.isShippable()) {
            System.out.printf(", weight: %.1fkg", tv.getWeight());
        }
        System.out.println(")");

        System.out.printf("- %s: $%.2f (%d available)%n", mobile.getName(), mobile.getPrice(), mobile.getQuantity());
        System.out.printf("- %s: $%.2f (%d available)%n", scratchCard.getName(), scratchCard.getPrice(), scratchCard.getQuantity());
        System.out.printf("%nCustomer Balance: $%.2f%n%n", customer.getBalance());

        // Demo: Successful checkout
        try {
            System.out.println("=== DEMO 1: Successful Checkout ===");
            customer.addToCart(cheese, 2);
            customer.addToCart(tv, 1);
            customer.addToCart(scratchCard, 5);
            
            System.out.println("Items added to cart:");
            for (CartItem item : customer.getCart().getItems()) {
                System.out.printf("- %s x%d%n", item.getProduct().getName(), item.getQuantity());
            }
            
            customer.checkout();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("\n");

        // Demo: Empty cart error
        try {
            System.out.println("=== DEMO 2: Empty Cart Error ===");
            customer.checkout();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("\n");

        // Demo: Insufficient balance error
        try {
            System.out.println("=== DEMO 3: Insufficient Balance Error ===");
            customer.addToCart(tv, 2); // This will cost more than remaining balance
            customer.checkout();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            customer.getCart().clear(); // Clear cart for next demo
        }

        System.out.println("\n");

        // Demo: Insufficient quantity error
        try {
            System.out.println("=== DEMO 4: Insufficient Quantity Error ===");
            customer.addToCart(tv, 15); // Only 9 TVs left after previous purchase
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        System.out.println("\n");

        // Demo: Final product quantities
        System.out.println("=== FINAL PRODUCT QUANTITIES ===");
        System.out.printf("- %s: %d remaining%n", cheese.getName(), cheese.getQuantity());
        System.out.printf("- %s: %d remaining%n", biscuits.getName(), biscuits.getQuantity());
        System.out.printf("- %s: %d remaining%n", tv.getName(), tv.getQuantity());
        System.out.printf("- %s: %d remaining%n", mobile.getName(), mobile.getQuantity());
        System.out.printf("- %s: %d remaining%n", scratchCard.getName(), scratchCard.getQuantity());
    }
}
