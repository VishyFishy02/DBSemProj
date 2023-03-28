/*Member attributes: UserID, Rental History, Warehouse Distance, Active status, Name, Email, Phone, Start Address*/

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Member{

	public static HashMap<String, String> createMember(Scanner scan) {
      String[] attributes = {"UserID", "Rental History", "Warehouse Distance", "Active Status", "Name", "Email", "Phone", "Start Address"};
    
    HashMap<String, String> newMember = new HashMap<String, String>();
    String value = "";
    for (String key : attributes) {
      System.out.println("Enter attribute value for " + key + ": ");
      value = scan.nextLine();
      newMember.put(key, value);
    }
    return newMember;
  }

  public static HashMap<String,String> searchMember(Scanner scan, ArrayList<HashMap<String, String>> list) {
    String key = "UserID";
    
    System.out.println("Enter UserID to search for: ");
    String value = scan.nextLine();
    
    for (HashMap<String,String> hm : list) {
      String v = hm.get(key);
      if (v.equals(value)) return hm;
    }
    return null;
  }

  public static HashMap<String, String> editMember(Scanner scan, ArrayList<HashMap<String, String>> list) {
      String[] attributes = {"UserID", "Rental History", "Warehouse Distance", "Active Status", "Name", "Email", "Phone", "Start Address"};
    HashMap<String, String> hm = searchMember(scan, list);
    if (hm == null) return null;
    for (int i = 0; i < attributes.length; i++) {
    System.out.println(i + " " + attributes[i]);
    }
    System.out.println("Select the number of the attribute to edit: ");
    String key = attributes[Integer.parseInt(scan.nextLine())];
    System.out.println("Select new value for attribute: ");
    String value = scan.nextLine();
    hm.replace(key, value);
    return hm;
  }

  public static void removeMember(Scanner scan, ArrayList<HashMap<String, String>> list) {
    HashMap<String,String> removeIt = searchMember(scan, list);
    if (removeIt != null) list.remove(removeIt);
  }
}
