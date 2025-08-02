package payment;

public class Cash implements PaymentMethod {
    @Override
    public void processPayment(double totalAmount) {
        System.out.println("Processing cash payment of $" + totalAmount);
    }

    @Override
    public void setPaymentMethod(String method) {
        System.out.println("Payment method set to: " + method);
    }

    @Override
    public String getPaymentMethod() {
        return "Cash";
    }
}
