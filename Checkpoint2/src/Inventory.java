import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

// Serial number, stock count, manufacturer, warranty, expiration date, location, year, model num
public class Inventory {
  public static HashMap<String, String> createInventoryItem(Scanner scan) {
      HashMap<String,String> inventoryItem = new     
                                HashMap<String,String>();

      // Fill in the map for all required items
      String[] inventoryFields = new String[]{"SerialNumber", "StockCount", "Manufacturer", "Warranty", "WarrantyExpDate", "Location", "Year", "ModelNumber"};
      
      // Stores input from user
      String userInput;
    
      for(String field : inventoryFields) {
        // Ask for input
        System.out.println("Enter a value for " + field + ".");
        if(field.contains("Warranty")) {
      	  System.out.print("Please enter in the format yyyy-MM-dd\n");
        }
        // Get and store the input
        userInput = scan.nextLine();
        inventoryItem.put(field, userInput);
      }
      

      return inventoryItem;
  
  }
}