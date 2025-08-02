package payment;
public class CreditCard implements PaymentMethod {
    @Override
    public void processPayment(double totalAmount) {
        System.out.println("Processing credit card payment of $" + totalAmount);
    }

    @Override
    public void setPaymentMethod(String method) {
       
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
}

