interface PaymentMethod {
    void processPayment(double totalAmount);
    void setPaymentMethod(String method);
    String getPaymentMethod();

    
}