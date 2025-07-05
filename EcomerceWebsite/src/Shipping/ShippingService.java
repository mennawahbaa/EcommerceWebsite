package Shipping;
import java.util.List;

import Product.Shippable;

public class ShippingService {
    public void processShipment(List<Shippable> items) {
        System.out.println("Processing shipment:");
        for (Shippable item : items) {
            System.out.println("- " + item.getName() + " (Weight: " + item.getWeight() + "kg)");
        }
    }
}
