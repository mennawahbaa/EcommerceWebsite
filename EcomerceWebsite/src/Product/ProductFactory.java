package Product;
import java.time.LocalDate;

public class ProductFactory {
    public static Product createProduct(String type, String name, double price, int quantity, 
                                      LocalDate expirationDate, double weight) {
        switch (type.toLowerCase()) {
            case "simple":
                return new Product(name, price, quantity);
            case "expirable":
                return new ExpirableProduct(name, price, quantity, expirationDate);
            case "shippable":
                return new ShippableProduct(name, price, quantity, weight);
            case "expirable-shippable":
                return new ExpirableShippableProduct(name, price, quantity, expirationDate, weight);
            default:
                throw new IllegalArgumentException("Invalid product type: " + type);
        }
    }
}
