package CheckOut;

public class CheckOutResult {
    private double subtotal;
    private double shippingFee;
    private double totalAmount;
    private double remainingBalance;

    public CheckOutResult(double subtotal, double shippingFee, double totalAmount, double remainingBalance) {
        this.subtotal = subtotal;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
        this.remainingBalance = remainingBalance;
    }

    // Getters
    public double getSubtotal() { return subtotal; }
    public double getShippingFee() { return shippingFee; }
    public double getTotalAmount() { return totalAmount; }
    public double getRemainingBalance() { return remainingBalance; }
}

