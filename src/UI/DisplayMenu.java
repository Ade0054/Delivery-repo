package UI;
import java.util.List;
import java.util.Scanner;

import model.Order;
import model.Products;
import model.User;
import payment.BankTransfer;
import payment.Cash;
import payment.CreditCard;
import payment.PaymentMethod;
import service.LoggerDetails;

import java.util.LinkedList;

public class DisplayMenu {
    private final Scanner scanner = new Scanner(System.in);
    

    public User promptUserForDetails() {
             System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        if(name.isEmpty()) {
            System.out.println("Name cannot be empty. Please try again.");
            return promptUserForDetails(); 
        }
        System.out.println("Welcome, " + name + "!" + "\n");
        return new User(name);
    }

    public int promptProductionSelection(List<Products> products) {
        int choice = 0;
        try {
            System.out.println("Available Products");
            printProductList(products);
            System.out.println("Enter the index of the product you want to select (0 to exit): ");
            choice = scanner.nextInt();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return choice;
    }

    public void printProductList(List<Products> products) {
        for(int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i).getId() + ". " + products.get(i).getName() + " - $" + products.get(i).getPrice());
        }
        System.out.println();
        System.out.println("********************************************");
    }

    public String promptUserForFurtherProductInput() {
        System.out.println("Press 'Y' to add more products or 'N' to proceed to checkout: ");
        return scanner.next();
    }

    public void displayUserCart(LinkedList<Products> products, double totalAmount) {
        System.out.println("\nYour current Cart:");
        printProductList(products);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println();
    }

    private void printUserCartForEdit (List<Products> cartList) {
        System.out.println("**************************************");
        if(cartList.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your cart: ");
            for (int i = 0; i < cartList.size(); i++) {
                System.out.println((i + 1) + ". " + cartList.get(i).getName() + " - $" + cartList.get(i).getPrice());
            }
            System.out.println("**************************************");
        }
    }

   public void promptUserToUpdateCart(List<Products> cart, List<Products> products) {
    boolean edit = true;
    while (edit && !cart.isEmpty()) {
        printUserCartForEdit(cart);

        System.out.println("\nOptions:");
        System.out.println("1. Do you want to remove an item?");
        System.out.println("2. Add an item to your cart?");
        System.out.println("3. Proceed to checkout");
        System.out.println("********************************************");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.println("Enter the product number to remove:");
                System.out.println("********************************************");
                int removeIndex = scanner.nextInt() - 1;
                if (removeIndex >= 0 && removeIndex < cart.size()) {
                    System.out.println(cart.get(removeIndex).getName() + " has been removed from your cart.");
                    cart.remove(removeIndex);
                } else {
                    System.out.println("Invalid product number.");
                }
                break;
            case 2:
                System.out.println("Enter the product number to be added:");
                int addIndex = scanner.nextInt() - 1;
                if (addIndex >= 0 && addIndex < products.size()) {
                    cart.add(products.get(addIndex));
                    System.out.println(products.get(addIndex).getName() + " has been added to your cart.");
                } else {
                    System.out.println("Invalid product number.");
                    
                }
                break;
            case 3:
                edit = false;
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
 }

 public void processPayment(double totalAmount, Order order, User user) {
    System.out.println("Select a payment method:");
    System.out.println("1. Credit Card");
    System.out.println("2. Bank Transfer");
    System.out.println("3. Cash");
    System.out.println("*****************************************");
    System.out.println();
    int choice = scanner.nextInt();
    PaymentMethod paymentMethod;
    switch (choice) {
        case 1:
            paymentMethod = new CreditCard();
            break;
        case 2:
            paymentMethod = new BankTransfer();
            break;
        case 3:
            paymentMethod = new Cash();
            break;
        default:
            System.out.println("Invalid choice. Please try again.");
            paymentMethod = new Cash();
            break;
    }

    paymentMethod.processPayment(totalAmount);

    if(paymentMethod instanceof CreditCard) {
        System.out.println("Payment method used: Credit Card");
    } else if(paymentMethod instanceof BankTransfer) {
        System.out.println("Payment method used: Bank Transfer");
    } else if(paymentMethod instanceof Cash) {
        System.out.println("Payment method used: Cash");
    }

    LoggerDetails.logUserOrder(user.getName(), order.getCart(), order.getTotalAmount());
 }
}
