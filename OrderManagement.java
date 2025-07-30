
public class OrderManagement {
    public static void main(String[] args) {
        DisplayMenu menu = new DisplayMenu();
        Products products = new Products();
        Order order = new Order();
        

        //User interaction and user processing flow
       menu.promptUserForDetails();
       order = displayUserIdAndGetUserInput(menu, products, order);
       menu.promptUserToUpdateCart(order.getCart(), products.getProductsList());
       menu.displayUserCart(order.getCart(), order.getTotalAmount());
       menu.processPayment(order.getTotalAmount());
       
    }


    private static Order displayUserIdAndGetUserInput(DisplayMenu menu, Products products, Order order) {
        String choice;
        do {
            int productIndex = menu.promptProductionSelection(products.getProductsList());
            order.addProductToOrderList(productIndex);
            System.out.println("print total:::: " + " $" + order.getTotalAmount());
            choice = menu.promptUserForFurtherProductInput();
        }while (choice.equalsIgnoreCase("Y"));
        return order;
    }



}