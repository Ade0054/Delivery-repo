package ui;


import model.Order;
import model.Products;
import model.User;
import payment.*;
import service.LoggerDetails;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
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

        JTextField searchArea = new JTextField(10);
        searchArea.setBorder(BorderFactory.createTitledBorder("Search"));

        searchArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterProducts(searchArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterProducts(searchArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterProducts(searchArea.getText());
            }
        });


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
                String selectedValue = productListModel.getElementAt(index);
                int productId = Integer.parseInt(selectedValue.split("\\.")[0].trim());
                Products selected = products.stream()
                                .filter(p -> p.getId() == productId).findFirst().orElse(null);
                if(selected != null) {
                    order.addProduct(selected);
                    cartListModel.addElement(selected.getName() + " - $" + selected.getPrice());
                    updateTotal();
                }

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
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(5, 5));
        leftPanel.add(searchArea, BorderLayout.NORTH);
        leftPanel.add(productScroll, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
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

    private void filterProducts(String query) {
        productListModel.clear();
        for(Products p : products) {
            String productText = p.getId() + ". " + p.getName() + " -$" + p.getPrice();
            if(p.getName().toLowerCase().contains(query.toLowerCase()) || String.valueOf(p.getId()).contains(query)) {
                productListModel.addElement(productText);
            }
        }
    }

}
