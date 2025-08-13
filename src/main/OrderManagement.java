package main;


import ui.DisplayMenu;
import ui.DisplayMenuSwing;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Order;
import model.Products;
import model.User;

public class OrderManagement {
    public static void main(String[] args) {

        boolean useGUI = true;

        if (useGUI) {
            // GUI mode
            SwingUtilities.invokeLater(() -> {
                Products products = new Products();
                List<Products> productList = products.getProductsList();
                String name = JOptionPane.showInputDialog("Enter your name:");
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty!");
                    return;
                }
                User user = new User(name);
                Order order = new Order();
                new DisplayMenuSwing(user, order, productList); // launches Swing UI
            });

        } else {

            DisplayMenu menu = new DisplayMenu();
            Products products = new Products();
            Order order = new Order();



            //User interaction and user processing flow
            User user = menu.promptUserForDetails();
            order = displayUserIdAndGetUserInput(menu, products, order);
            menu.promptUserToUpdateCart(order.getCart(), products.getProductsList());
            menu.displayUserCart(order.getCart(), order.getTotalAmount());
            menu.processPayment(order.getTotalAmount(), order, user);

        }

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