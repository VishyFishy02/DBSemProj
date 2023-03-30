// Tuhin's files
import java.util.Scanner;

import java.util.List;
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

public class Drones {

  String[] options = new String[]{"serial number", "stock count", "manufacturer", "warranty", "expiration date", "location", "year", "model number", "equipment type", "description", "inventory id", "size", "weight"};

  // Add a drone
public static HashMap<String, String> createDrone(Scanner scan, Connection link) throws SQLException, ParseException {

    // Fill in all shared fields from equipment
    HashMap<String, String> newDrone = Inventory.createInventoryItem(scan);

    // Now get all of a drone's fields
    String[] droneFields = new String[]{"fleet ID", "name/description", "volume capacity", "weight capacity", "distance autonomy", "max speed", "active status"};

    String userInput;
    
    for (String field : droneFields) {
      System.out.println("Enter a value for " + field + ".");
      userInput = scan.nextLine();
      newDrone.put(field, userInput);
      
    }
   
    String query = "insert into DRONE(Location, FleetID, SerialNumber, WeightCapacity, VolumeCapacity, WarrantyExpDate, MaxSpeed, ActiveStatus, StockCount, ModelNumber) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement stmt = link.prepareStatement(query);
    
    stmt.setString(1, newDrone.get("location"));
    stmt.setInt(2, Integer.parseInt(newDrone.get("fleet ID")));
    stmt.setInt(3, Integer.parseInt(newDrone.get("serial number")));
    stmt.setInt(4, Integer.parseInt(newDrone.get("weight capacity")));
    stmt.setInt(5, Integer.parseInt(newDrone.get("volume capacity")));
    
    java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(newDrone.get("expiration date"));
    java.sql.Date expDate = new java.sql.Date(utilDate.getTime());
    
    stmt.setDate(6, expDate);
    stmt.setInt(7, Integer.parseInt(newDrone.get("max speed")));
    stmt.setBoolean(8, Boolean.parseBoolean(newDrone.get("active status")));
    stmt.setInt(9, Integer.parseInt(newDrone.get("stock count")));
    stmt.setInt(10, Integer.parseInt(newDrone.get("model number")));
    
    stmt.executeUpdate();
    System.out.println("Drone successfully Added");
    return newDrone;
  }

  // Edit a drone
  public static ResultSet   
     editDrone(ArrayList<HashMap<String, String>> list, Scanner     
  scan, Connection link) throws SQLException {

    String[] options = new String[]{"FleetID", "name/description", "VolumeCapacity", "WeightCapacity", "distance autonomy", "MaxSpeed", "ActiveStatus"};
    // Search for the desired drone
    ResultSet drone = searchDrones(list, scan, link);
    int serialNum = drone.getInt("SerialNumber");
    
    if (drone == null) return null;
    for (int i = 0; i < options.length; i++) {
    System.out.println(i + " " + options[i]);
    }
    System.out.println("Select the number of the attribute to edit: ");
    String key = options[Integer.parseInt(scan.nextLine())];
    
    
    System.out.println("Select new value for attribute: ");
    String value = scan.nextLine();
    // edit the drone entity
    
    
    String query = "UPDATE DRONE SET "+ key +"= \'" + value +"\' WHERE DRONE.SerialNumber = "+ serialNum;
    Statement stmt = link.createStatement();
    stmt.executeUpdate(query);
    System.out.println("Drone Successfully Updated!");
    
    return drone;
  }

  // Remove a drone
  public static void deleteDrone(ArrayList<HashMap<String, String>> list, Scanner scan, Connection link) throws SQLException {

    // Search for the desired drone
    ResultSet rslt = searchDrones(list, scan, link);
    int serialNum = rslt.getInt("SerialNumber");
    
    // Remove the drone from the db
    
    String query = "DELETE FROM DRONE Where Drone.SerialNumber = " + serialNum;
    Statement stmt = link.createStatement();
    stmt.executeUpdate(query);
    System.out.println("Drone has been removed"); 
	
  }

  // Search for a drone
   public static ResultSet searchDrones(ArrayList<HashMap<String, 
  String>> list, Scanner scan, Connection link) throws SQLException {

     // Ask for the drone's serial #
     System.out.println("Please enter the serial # of the desired drone");
     String droneNum = scan.nextLine();

     /* search the db
     for(HashMap<String,String> drone : list) {
       String val = drone.get("serial number");

       if(val.equals(droneNum)) {
         return drone;
       }
     }
	*/

     String query = "SELECT * FROM DRONE Where Drone.SerialNumber = " + droneNum;
     Statement stmt = link.createStatement();
     ResultSet rslt = stmt.executeQuery(query);
     
     return rslt; 
   }
}
