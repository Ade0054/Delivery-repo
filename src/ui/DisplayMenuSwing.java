package ui;


import model.Order;
import model.Products;
import model.User;
import payment.*;
import service.LoggerDetails;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DisplayMenuSwing extends JFrame {
    private final Order order;
    private final User user;
    private final List<Products> products;

    private final DefaultListModel<String> productListModel = new DefaultListModel<>();
    private final DefaultListModel<String> cartListModel = new DefaultListModel<>();
    private final JLabel totalLabel = new JLabel("Total: $0.00");

    public DisplayMenuSwing(User user, Order order, List<Products> products) {
        this.user = user;
        this.order = order;
        this.products = products;

        setTitle("Delivery App - Welcome " + user.getName());
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- LEFT: Product List ---
        for (Products p : products) {
            productListModel.addElement(p.getId() + ". " + p.getName() + " - $" + p.getPrice());
        }
        JList<String> productList = new JList<>(productListModel);
        JScrollPane productScroll = new JScrollPane(productList);
        productScroll.setBorder(BorderFactory.createTitledBorder("Available Products"));

        // --- CENTER: Cart List ---
        JList<String> cartList = new JList<>(cartListModel);
        JScrollPane cartScroll = new JScrollPane(cartList);
        cartScroll.setBorder(BorderFactory.createTitledBorder("Your Cart"));

        // --- RIGHT: Buttons Panel ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 5, 5));

        JButton addButton = new JButton("Add to Cart");
        addButton.addActionListener(e -> {
            int index = productList.getSelectedIndex();
            if (index != -1) {
                Products selected = products.get(index);
                order.addProduct(selected);
                cartListModel.addElement(selected.getName() + " - $" + selected.getPrice());
                updateTotal();
            }
        });

        JButton removeButton = new JButton("Remove from Cart");
        removeButton.addActionListener(e -> {
            int index = cartList.getSelectedIndex();
            if (index != -1) {
                order.getCart().remove(index);
                cartListModel.remove(index);
                updateTotal();
            }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> processPayment());

        JButton clearCartButton = new JButton("Clear Cart");
        clearCartButton.addActionListener(e -> {
            order.getCart().clear();
            cartListModel.clear();
            updateTotal();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearCartButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(exitButton);

        // --- BOTTOM: Total Price ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(totalLabel);

        // --- Add components ---
        add(productScroll, BorderLayout.WEST);
        add(cartScroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateTotal() {
        totalLabel.setText(String.format("Total: $%.2f", order.getTotalAmount()));
    }

    private void processPayment() {
        if (order.getCart().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] options = {"Credit Card", "Bank Transfer", "Cash"};
        int choice = JOptionPane.showOptionDialog(this,
                "Select payment method",
                "Payment",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        PaymentMethod paymentMethod;
        switch (choice) {
            case 0:
                paymentMethod = new CreditCard();
                break;
            case 1:
                paymentMethod = new BankTransfer();
                break;
            case 2:
            default:
                paymentMethod = new Cash();
        }

        paymentMethod.processPayment(order.getTotalAmount());
        LoggerDetails.logUserOrder(user.getName(), order.getCart(), order.getTotalAmount());

        JOptionPane.showMessageDialog(this,
                "Order completed!\nTotal: $" + order.getTotalAmount() +
                        "\nPayment: " + options[choice]);

        dispose(); // Close after payment
    }

}
