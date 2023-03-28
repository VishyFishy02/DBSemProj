import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

// Equpipment: Serial Number, stock count, manufacturer, warranty expiration date, location, year, model num Equipment type, description, inventory id, size, weight
public class Equipment {

  public static String[] options = new String[] { "serial number", "stock count", "manufacturer", "warranty",
      "expiration date", "location", "year", "model number", "equipment type", "description", "inventory id", "size",
      "weight" };

  // search
  public static HashMap<String, String> searchEquipment(ArrayList<HashMap<String, String>> list, Scanner scan) {

    for (int i = 0; i < options.length; i++) {
      System.out.println(i + " " + options[i]);
    }
    System.out.print("Enter the criteria you wish to search by: ");
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

    System.out.println("The equipment you were looking for could not be found.");
    return null;
  }

  // edit
  public static HashMap<String, String> editEquipment(ArrayList<HashMap<String, String>> list, Scanner scan) {

    HashMap<String, String> edit = searchEquipment(list, scan);

    for (int i = 0; i < options.length; i++) {
      System.out.println(i + " " + options[i]);
    }
    System.out.print("Enter the criteria you wish to edit: ");
    int choice = Integer.parseInt(scan.nextLine());
    String key = options[choice];

    System.out.print("Enter the new " + key + " you would like to insert: ");
    String newVal = scan.nextLine();
    edit.replace(key, newVal);

    return edit;
  }

  // delete
  public static void deleteEquipment(ArrayList<HashMap<String, String>> list, Scanner scan) {

    HashMap<String, String> delete = searchEquipment(list, scan);
    list.remove(delete);
    
  }

  // add
  public static HashMap<String, String> addEquipment(Scanner scan) {
    HashMap<String, String> newEquipment = new HashMap<>();
    newEquipment = Inventory.createInventoryItem(scan);

    System.out.println("Enter equipment type:");
    String type = scan.nextLine();
    newEquipment.put("type", type);

    System.out.println("Enter equipment description:");
    String description = scan.nextLine();
    newEquipment.put("description", description);

    System.out.println("Enter inventory id:");
    String id = scan.nextLine();
    newEquipment.put("inventory id", id);

    System.out.println("Enter weight:");
    String weight = scan.nextLine();
    newEquipment.put("weight", weight);

    System.out.println("Enter size:");
    String size = scan.nextLine();
    newEquipment.put("size", size);

    return newEquipment;
  }
}
