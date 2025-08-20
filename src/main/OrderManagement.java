package main;


import dao.ProductDAO;
import db.DbConnectivity;
import ui.DisplayMenu;
import ui.DisplayMenuSwing;

import java.sql.Connection;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.Order;
import model.Products;
import model.User;

public class OrderManagement {
    public static void main(String[] args) {

        boolean useGUI = true;

        DbConnectivity connectivity = new DbConnectivity();
        Connection connection = connectivity.connect("jdbc:postgresql://localhost:5432/products_db", "postgres", "Methodoverriding@$1");

        //DAO operations
        ProductDAO dao = new ProductDAO();
        dao.createTable(connection, "Products");
//        dao.insertAllProducts(connection);
        List<Products> productFromDb = dao.getAllProducts(connection);
        Products products = new Products();
        products.setProductsList(productFromDb);
        List<Products> productList = products.getProductsList();


        if (useGUI) {
            // GUI mode
            SwingUtilities.invokeLater(() -> {
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
            Order order = new Order();



            //User interaction and user processing flow
            User user = menu.promptUserForDetails();
            order = displayUserIdAndGetUserInput(menu, productList, order);
            menu.promptUserToUpdateCart(order.getCart(), productList);
            menu.displayUserCart(order.getCart(), order.getTotalAmount());
            menu.processPayment(order.getTotalAmount(), order, user);

        }

    }


    private static Order displayUserIdAndGetUserInput(DisplayMenu menu, List<Products> products, Order order) {
        String choice;
        do {
            int productIndex = menu.promptProductionSelection(products);
            order.addProductToOrderList(productIndex, products);
            System.out.println("print total:::: " + " $" + order.getTotalAmount());
            choice = menu.promptUserForFurtherProductInput();
        }while (choice.equalsIgnoreCase("Y"));
        return order;
    }



}