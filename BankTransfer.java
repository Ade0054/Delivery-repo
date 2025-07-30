public class BankTransfer implements PaymentMethod {
     @Override
    public void processPayment(double totalAmount) {
        System.out.println("Processing bank transfer payment of $" + totalAmount);
    }

    @Override
    public void setPaymentMethod(String method) {
        System.out.println("Payment method set to: " + method);
    }

    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
}
