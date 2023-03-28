import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

// Serial number, stock count, manufacturer, warranty, expiration date, location, year, model num
public class Inventory {
  public static HashMap<String,String> createInventoryItem(Scanner scan) {
      HashMap<String,String> inventoryItem = new     
                                HashMap<String,String>();

      // Fill in the map for all required items
      String[] inventoryFields = new String[]{"serial number", "stock count", "manufacturer", "warranty", "expiration date", "location", "year", "model number"};

      // Stores input from user
      String userInput;
    
      for(String field : inventoryFields) {
        // Ask for input
        System.out.println("Enter a value for " + field + ".");

        // Get and store the input
        userInput = scan.nextLine();
        inventoryItem.put(field, userInput);
      }
      

      return inventoryItem;
  
  }
}