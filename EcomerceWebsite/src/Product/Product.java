package Product;

public class Product {
    protected String name;
    protected double price;
    protected int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }



	public boolean isExpirable() {
        return false;
    }

    public boolean isShippable() {
        return false;
    }

    public double getWeight() {
        return 0.0;
    }

    public boolean isAvailable(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }

    public boolean reduceQuantity(int amount) {
        if (this.quantity >= amount) {
            this.quantity -= amount;
            return true;
        }
        return false;
    }

    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
