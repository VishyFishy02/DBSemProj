import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
  
public static String[] options = new String[]{"address", "city", "phone number", "manager name", "storage capacity", "drone capacity"};
  
public static HashMap<String, String> createWarehouse(Scanner scan) {
//Warehouses: Address, city, phone number, manager name, storage capcity, drone capcity
  HashMap<String, String> warehouse = new HashMap<String, String>();

  System.out.println("Enter the warehouse address: ");
  String address = scan.nextLine();
  warehouse.put("address", address);

  System.out.println("Enter the warehouse city: ");
  String city = scan.nextLine();
  warehouse.put("city", city);

  System.out.println("Enter the warehouse phone number in the format (XXX) XXX-XXXX: ");
  String phone = scan.nextLine();
  warehouse.put("phone number", phone);

  System.out.println("Enter the warehouse manager's name: ");
  String mngr = scan.nextLine();
  warehouse.put("manager name", mngr);

  System.out.println("Enter the warehouse's storage capacity: ");
  String storCap = scan.nextLine();
  warehouse.put("storage capacity", storCap);

  System.out.println("Enter the warehouse's drone capacity: ");
  String droneCap = scan.nextLine();
  warehouse.put("drone capacity", droneCap);
  
  return warehouse;
}

public static HashMap<String, String> searchForWarehouse(ArrayList<HashMap<String, String>> list, Scanner scan) {

  for (int i = 0; i < options.length; i++) {
    System.out.println(i + " " + options[i]);
  }
  System.out.print("Enter the number of the criteria you wish to search by: ");
  int choice = Integer.parseInt(scan.nextLine());
  String key = options[choice];

  System.out.print("Enter the " + key + " you wish to find: ");
  String toFind = scan.nextLine();
  
  for (HashMap<String, String> map : list) {
    String value = map.get(key);
    if (value.equals(toFind)) {
      return map;
    }
  }
    
  System.out.println("The warehouse you were looking for could not be found.");
  return null;
}


public static HashMap<String, String> editWarehouse(ArrayList<HashMap<String, String>> list, Scanner scan) {

  HashMap<String, String> edit = searchForWarehouse(list, scan);
  for (int i = 0; i < options.length; i++) {
    System.out.println(i + " " + options[i]);
  }
  System.out.print("Enter the number of the criteria you wish to edit: ");
  int choice = Integer.parseInt(scan.nextLine());
  String key = options[choice];

  System.out.print("Enter the new " + key + " you would like to insert: ");
  String newVal = scan.nextLine();
  edit.replace(key, newVal);

  return edit;
}


public static void deleteWarehouse(ArrayList<HashMap<String, String>> list, Scanner scan) {

  HashMap<String, String> delete = searchForWarehouse(list, scan);
  list.remove(delete);

}
}


