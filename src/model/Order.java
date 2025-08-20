package model;

import java.util.LinkedList;
import java.util.List;
// import java.util.Scanner;

public class Order {
    // private String productName;
    // private double productPrice;
    double totalAmount;

    private final Products products = new Products();

  
    LinkedList<Products> cart = new LinkedList<>();

    public Order() {
        
    }

    

    public LinkedList<Products> getCart() {
      return cart;
    }


    public void setCart(LinkedList<Products> cart) {
        this.cart = cart;
    }



    public double getTotalAmount() {
      return getCart().stream().map(Products::getPrice).reduce(0d, Double::sum);
              
      // return totalAmount;
    }



    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }


    public void placeOrder(List<Products> products) {
        // Scanner scanner = new Scanner(System.in);
        // boolean shopping = true;

        
      //   while(shopping) {
          
      //    System.out.println("Available Products and Prices: ");
      //   System.out.println("********************************************");

      //   for(int i = 0; i < products.size(); i++){
      //       System.out.println((i + 1) + "." + products.get(i).getName() + " - $" + products.get(i).getPrice());
      //   }
      //   System.out.println();
      //   System.out.println("********************************************");
           

      //   System.out.println("\n0. Exit");
      //   System.out.println();
      //   System.out.println("********************************************");
      //   int choice = scanner.nextInt();

      //   if(choice == 0) {
      //       shopping = false;
      //   } else if(choice > 0 && choice <= products.size()) {

      //       cart.add(products.get(choice -1));
            
      //       System.out.println(products.get(choice -1).getName() + " has been added to cart for $" + products.get(choice -1).getPrice());
      //   }else {
      //       System.out.println("Place an order by entering a number between 1 and " + products.size());
      //   }
      // }

      // boolean edit = true;
      // while(edit && !cart.isEmpty()) {
      //   System.out.println("\nYour Cart: ");
      //   double total = 0.00;
      //  for (int i = 0; i < cart.size(); i++){
      //       System.out.println((i + 1) + ". " + cart.get(i).getName() + " - $" + cart.get(i).getPrice());
      //       total += cart.get(i).getPrice();
      //   }
      //   System.out.println("Total: $" + total);

      //   System.out.println("\nOptions:");
      //   System.out.println("1. Shey u wan comot something?");
      //  System.out.println("2. Replace am with another thing sharp sharp, we no dey refund?");
      //   System.out.println("3. Oya dey go na, to tiff dey your eye");
        

      //   System.out.println("********************************************");

      //   int option = scanner.nextInt();

      //   switch(option) {
      //     case 1:
      //       System.out.println("Enter the product number to remove:");
      //       System.out.println("********************************************");
      //       int removeIndex = scanner.nextInt() - 1;
      //       if(removeIndex >= 0 && removeIndex < cart.size()) {
      //         System.out.println(cart.get(removeIndex).getName() + " has been removed from your cart.");
      //         cart.remove(removeIndex);
      //       } else {
      //         System.out.println("Invalid product number.");
      //       }
      //       break;
      //       case 2:
      //       System.out.println("Enter the product number to be added:");
      //       int addIndex = scanner.nextInt() - 1;
      //       if(addIndex >= 0 && addIndex < products.size()) {
      //         cart.add(products.get(addIndex));
      //         System.out.println(products.get(addIndex).getName() + " has been added to your cart.");
      //       } else {
      //         System.out.println("Invalid product number.");
      //       }
      //       break;
      //     case 3:
      //       edit = false;
      //       break;
      //     default:
      //       System.out.println("Invalid option. Please try again.");
      //   }
      // }

      System.out.println("\nUpdated Cart: ");
      double total = 0.00;
      for (Products product : cart) {
        System.out.println("- " + product.getName() + " - $" + product.getPrice());
        total += product.getPrice();
      }
        System.out.println("Total: $" + total);
    }

    public void addProductToOrderList(int productIndex, List<Products> productList) {
        if(productList == null) {
            System.out.println("Product list is empty");
            return;
        }
      for(Products product : productList) {
          if(product.getId() == productIndex) {
              cart.add(product);
              System.out.println(product.getName() + " has been added to your cart for $" + product.getPrice());
              break;
          }
      }
      getTheTotalAmount();
      
    }

    public void getTheTotalAmount() {
      double total = 0.00;
      for (Products product : cart) {
        System.out.println("Product: " + product.getName() + ", Price: $" + product.getPrice());
          total = total + product.getPrice();
      }
      totalAmount = total;
    }

    public void addProduct(Products product) {
        cart.add(product);
        totalAmount += product.getPrice();
    }

}
