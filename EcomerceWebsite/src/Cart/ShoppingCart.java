package Cart;
import java.util.ArrayList;
import java.util.List;

import Product.ExpirableProduct;
import Product.ExpirableShippableProduct;
import Product.Product;

public class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) throws Exception {
        if (!product.isAvailable(quantity)) {
            throw new Exception("Insufficient quantity available for " + product.getName() + 
                              ". Available: " + product.getQuantity() + ", Requested: " + quantity);
        }
        if (product instanceof ExpirableShippableProduct) {
            ExpirableShippableProduct expShipProduct = (ExpirableShippableProduct) product;
            if (expShipProduct.isExpired()) {
                throw new Exception("Product " + product.getName() + " has expired and cannot be added to cart");
            }
        }
        else if (product.isExpirable()) {
            ExpirableProduct expProduct = (ExpirableProduct) product;
            if (expProduct.isExpired()) {
                throw new Exception("Product " + product.getName() + " has expired and cannot be added to cart");
            }
        }



        // Check if product already exists in cart
        CartItem existingItem = null;
        for (CartItem item : items) {
            if (item.getProduct() == product) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            int totalQuantity = existingItem.getQuantity() + quantity;
            if (!product.isAvailable(totalQuantity)) {
                throw new Exception("Insufficient quantity available for " + product.getName() + 
                                  ". Available: " + product.getQuantity() + ", Total requested: " + totalQuantity);
            }
            existingItem.setQuantity(totalQuantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    public void removeItem(Product product) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProduct() == product) {
                items.remove(i);
                break;
            }
        }
    }

    public double getSubtotal() {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public double getTotalWeight() {
        double totalWeight = 0.0;
        for (CartItem item : items) {
            totalWeight += item.getTotalWeight();
        }
        return totalWeight;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }
}
