package Customer;
import java.util.ArrayList;
import java.util.List;

import Cart.CartItem;
import Cart.ShoppingCart;
import CheckOut.CheckOutResult;
import Product.Product;
import Product.Shippable;
import Shipping.ShippingService;

public class Customer {
    private String name;
    private double balance;
    private ShoppingCart cart;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.cart = new ShoppingCart();
    }

    public void addToCart(Product product, int quantity) throws Exception {
        cart.addItem(product, quantity);
    }

    public void removeFromCart(Product product) {
        cart.removeItem(product);
    }
    public CheckOutResult checkout() throws Exception {
        if (cart.isEmpty()) {
            throw new Exception("Cart is empty. Cannot proceed with checkout.");
        }

        double subtotal = cart.getSubtotal();
        double shippingFee = calculateShippingFee();
        double totalAmount = subtotal + shippingFee;

        if (balance < totalAmount) {
            throw new Exception(String.format("Insufficient balance. Required: $%.2f, Available: $%.2f", 
                                             totalAmount, balance));
        }

        // Process payment
        balance -= totalAmount;

        // Reduce product quantities
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        // Print checkout details
        System.out.println("=== CHECKOUT DETAILS ===");
        System.out.println("Customer: " + name);
        System.out.println("\nItems purchased:");
        for (CartItem item : cart.getItems()) {
            System.out.printf("- %s x%d @ $%.2f each = $%.2f%n", 
                             item.getProduct().getName(), item.getQuantity(), 
                             item.getProduct().getPrice(), item.getTotalPrice());
        }
        System.out.printf("%nOrder Subtotal: $%.2f%n", subtotal);
        System.out.printf("Shipping Fees: $%.2f%n", shippingFee);
        System.out.printf("Paid Amount: $%.2f%n", totalAmount);
        System.out.printf("Customer Balance After Payment: $%.2f%n", balance);
        System.out.println("========================");

        // Clear cart after successful checkout
        cart.clear();

        return new CheckOutResult(subtotal, shippingFee, totalAmount, balance);
    }

    private double calculateShippingFee() {
        double totalWeight = 0.0;
        boolean hasShippableItems = false;
        List<Shippable> itemsToShip = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().isShippable()) {
                totalWeight += item.getTotalWeight();
                hasShippableItems = true;
                Product product = item.getProduct();
                for (int i = 0; i < item.getQuantity(); i++) {
                    itemsToShip.add((Shippable) product);
                }
            }
        }

        if (!hasShippableItems) {
            return 0.0;
        }

        // Shipping fee calculation: $5 base fee + $2 per kg
        double baseFee = 5.00;
        double weightFee = totalWeight * 2.00;
        
        
        ShippingService shippingService = new ShippingService();
        shippingService.processShipment(itemsToShip);
        return baseFee + weightFee;
    }

    // Getters
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public ShoppingCart getCart() { return cart; }

}