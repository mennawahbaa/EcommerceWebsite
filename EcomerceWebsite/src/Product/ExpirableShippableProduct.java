package Product;
import java.time.LocalDate;

public class ExpirableShippableProduct extends Product implements Shippable{
    private LocalDate expirationDate;
    private double weight;

    public ExpirableShippableProduct(String name, double price, int quantity, 
                                   LocalDate expirationDate, double weight) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    @Override
    public boolean isExpirable() {
        return true;
    }

    @Override
    public boolean isShippable() {
        return true;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}