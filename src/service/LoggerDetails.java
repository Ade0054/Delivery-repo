package service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Products;



public class LoggerDetails {
    public static void logUserOrder(String name, List<Products> cart, double totalAmount) {
        System.out.println("Logging user order details...");
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(name).append(" ordered: [");

           for (Products product : cart) {
            logEntry.append(product.getName())
            .append(" - $")
            .append(product.getPrice())
            .append(", ");
      }

if (!cart.isEmpty()) {
    logEntry.setLength(logEntry.length() - 2);
}

logEntry.append("]").append(" Total Amount: $").append(totalAmount);

System.out.println("Log Entry: " + logEntry.toString());



try(FileWriter writer = new FileWriter("orderLog.txt", false)) {
    writer.write(logEntry.toString() + "\n");
    System.out.println("Order details logged successfully." );
}catch (IOException e) {
    System.out.println("Error writing to file: " + e.getMessage());
}
    }
}
