import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;

// Equpipment: Serial Number, stock count, manufacturer, warranty expiration date, location, year, model num Equipment type, description, inventory id, size, weight
public class Equipment {

  public static String[] options = new String[] { "serial number", "stock count", "manufacturer", "warranty",
      "expiration date", "location", "year", "model number", "equipment type", "description", "inventory id", "size",
      "weight" };

  // search
  public static ResultSet searchEquipment(ArrayList<HashMap<String, String>> list, Scanner scan, Connection link) throws SQLException {

	// Ask for the equipment's serial #
	System.out.println("Please enter the serial # of the desired equipment");
	String equipNum = scan.nextLine();

	String query = "SELECT Equipment.SerialNumber FROM EQUIPMENT Where Equipment.SerialNumber = " + equipNum;
	Statement stmt = link.createStatement();
	ResultSet rslt = stmt.executeQuery(query);
	return rslt; 
  }

  // edit
  public static ResultSet editEquipment(ArrayList<HashMap<String, String>> list, Scanner scan, Connection link) throws SQLException {

	String[] options = new String[]{"type", "equipment type", "description", "fleet id", "inventory id", "weight", "size", "arrival date"};
			
	// Search for the desired equipment
	ResultSet equipment = searchEquipment(list, scan, link);
	int serialNum = equipment.getInt("SerialNumber");
	  
	ResultSet edit = searchEquipment(list, scan, link);
    

	if (equipment == null) return null;
    for (int i = 0; i < options.length; i++) {
    	System.out.println(i + " " + options[i]);
    }
    
    System.out.println("Select the number of the attribute to edit: ");
    String key = options[Integer.parseInt(scan.nextLine())];

    System.out.println("Select new value for attribute: ");
    String value = scan.nextLine();
    
    
    
    String query = "UPDATE DRONE SET "+ key +"= \'" + value +"\' WHERE DRONE.SerialNumber = "+ serialNum;
    Statement stmt = link.createStatement();
    stmt.executeUpdate(query);
    
    return equipment;
   
  }

  // delete
  public static void deleteEquipment(ArrayList<HashMap<String, String>> list, Scanner scan, Connection link) throws SQLException {

    ResultSet delete = searchEquipment(list, scan, link);
    
    // Search for the desired equipment
    ResultSet rslt = searchEquipment(list, scan, link);
    int serialNum = rslt.getInt("SerialNumber");
    
    String query = "DELETE FROM EQUIPMENT Where Equipment.SerialNumber = " + serialNum;
    Statement stmt = link.createStatement();
    stmt.executeUpdate(query);
    System.out.println("Equipment has been removed"); 
    
  }

  // add
  public static HashMap<String, String> addEquipment(Scanner scan, Connection link) throws SQLException, ParseException {
	  
	// Create a new map for the equipment being added
    HashMap<String, String> newEquipment = new HashMap<>();
    newEquipment = Inventory.createInventoryItem(scan);
    
    // All of the unique fields to equipment
    String[] equipmentFields = new String[]{"type", "equipment type", "description", "fleet id", "inventory id", "weight", "size", "arrival date"};
    String userInput;
    
    
    // Store all of the unique fields
    for (String field : equipmentFields) {
        System.out.println("Enter a value for " + field + ".");
        
        if(field.equals("arrival date")) {
      	  System.out.print(" Please enter in the format yyyy-MM-dd\n");
        }
        userInput = scan.nextLine();
        newEquipment.put(field, userInput);
        
      }
    
    // Prepare a statement to insert data into the equipment table
    String query = "insert into EQUIPMENT(Location, Description, SerialNumber, InventoryID, ArrivalDate, FleetID, EquipmentType, Weight, Size, StockCount, ModelNumber) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement stmt =  link.prepareStatement(query);
    
    stmt.setString(1, newEquipment.get("location"));
    stmt.setString(2, newEquipment.get("description"));
    stmt.setInt(3, Integer.parseInt(newEquipment.get("serial number")));
    stmt.setInt(4, Integer.parseInt(newEquipment.get("inventory id")));
    
    // Have to extract the date ina specific format
    java.util.Date arrDate = new SimpleDateFormat("yyyy-MM-dd").parse(newEquipment.get("arrival date"));
    java.sql.Date expDate = new java.sql.Date(arrDate.getTime());
    stmt.setDate(5, expDate);
    
    stmt.setInt(6, Integer.parseInt(newEquipment.get("fleet id")));
    stmt.setString(7, newEquipment.get("equipment type"));
    stmt.setInt(8, Integer.parseInt(newEquipment.get("weight")));
    stmt.setInt(9, Integer.parseInt(newEquipment.get("size")));
    stmt.setInt(10, Integer.parseInt(newEquipment.get("stock count")));
    stmt.setInt(11, Integer.parseInt(newEquipment.get("model number")));
    
    
    System.out.println("Equipment has been added successfully");
    return newEquipment;
  }
}
