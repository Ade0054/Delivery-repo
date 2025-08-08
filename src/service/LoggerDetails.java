package service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.time.format.DateTimeFormatter;

import model.Products;



public class LoggerDetails {
    public static void logUserOrder(String name, List<Products> cart, double totalAmount) {
        System.out.println("Logging user order details...");
        StringBuilder logEntry = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String safeName = name.replaceAll("//s+", "_").toLowerCase();
        String safeFileName = "src/resources/order_" + safeName + ".txt";

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
logEntry.append("\n");
logEntry.append("Order logged at: ").append(now.format(formatter));
logEntry.append("\n");

//System.out.println("Log Entry: " + logEntry.toString());



try(FileWriter writer = new FileWriter(safeFileName, true)) {
    writer.write(logEntry.toString() + "\n");
    System.out.println("Order details logged successfully." + safeFileName );
}catch (IOException e) {
    System.out.println("Error writing to file: " + e.getMessage());
}
    }
}
