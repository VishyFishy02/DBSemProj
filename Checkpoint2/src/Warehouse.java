import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
  
public static String[] options = new String[]{"address", "city", "phone number", "manager name", "storage capacity", "drone capacity"};
  
public static HashMap<String, String> createWarehouse(Scanner scan, Connection link) throws SQLException, ParseException {
//Warehouses: Address, city, phone number, manager name, storage capcity, drone capcity
  HashMap<String, String> warehouse = new HashMap<String, String>();
  
  String userInput;
  
  for (String field : options) {
    System.out.println("Enter a value for " + field + ":");
    
    userInput = scan.nextLine();
    warehouse.put(field, userInput);
    
  }
  
  String query = "insert into WAREHOUSE(City, Address, PhoneNumber, ManagerName, StorageCapacity, DroneCapacity) values (?,?,?,?,?,?)";
  PreparedStatement stmt = link.prepareStatement(query);
  
  stmt.setString(1,  warehouse.get("city"));
  stmt.setString(2,  warehouse.get("address"));
  stmt.setString(3,  warehouse.get("phone number"));
  stmt.setString(4,  warehouse.get("manager name"));
  stmt.setInt(5,  warehouse.get("storage capacity"));
  stmt.setInt(6,  warehouse.get("drone capacity"));

  stmt.executeUpdate();
  System.out.println("Warehouse successfully added");
  
  return warehouse;
}

public static ResultSet searchForWarehouse(ArrayList<HashMap<String, String>> list, Scanner scan, Connection link) throws SQLException {

  System.out.print("Enter the address of the warehouse you would like to find: ");
  String add = scan.nextLine();

  String query = "SELECT * FROM WAREHOUSE WHERE Warehouse.Address = \'" + add + "\'";
  Statement stmt = line.createStatement();
  ResultSet rslt = stmt.executeQuery(query);
  
  if (!rslt.next()) {
      System.out.println("The warehouse you were looking for could not be found.");
  }
  return rslt;
}


public static ResultSet editWarehouse(ArrayList<HashMap<String, String>> list, Scanner scan, Connection link) throws SQLException {

  ResultSet edit = searchForWarehouse(list, scan, link);
  String add = edit.getString("Address");
  
  for (int i = 0; i < options.length; i++) {
    System.out.println(i + " " + options[i]);
  }
  System.out.print("Enter the number of the criteria you wish to edit: ");
  int choice = Integer.parseInt(scan.nextLine());
  String key = options[choice];

  System.out.print("Enter the new " + key + " you would like to insert: ");
  String newVal = scan.nextLine();
  
  String query = "UPDATE WAREHOUSE SET "+ key +"= \'" + newVal +"\' WHERE Warehouse.Address = "+ add;
  Statement stmt = link.createStatement();
  stmt.executeUpdate(query);

  return edit;
}


public static void deleteWarehouse(ArrayList<HashMap<String, String>> list, Scanner scan, Connection link) throws SQLException {

  HashMap<String, String> delete = searchForWarehouse(list, scan);
  list.remove(delete);
  
  ResultSet rslt = searchForWarehouse(list, scan, link);
  String add = rslt.getString("Address");
  
  // Remove the drone from the db
  
  String query = "DELETE FROM WAREHOUSE Where Warehouse.Address = " + add;
  Statement stmt = link.createStatement();
  stmt.executeUpdate(query);
  System.out.println("Warehouse has been removed"); 

}
}


