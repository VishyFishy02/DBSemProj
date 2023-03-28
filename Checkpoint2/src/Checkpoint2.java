import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

class Checkpoint2 {

  // Nelly
  static ArrayList<HashMap<String, String>> memberList = new ArrayList<HashMap<String, String>>();
  // Lisha
  static ArrayList<HashMap<String, String>> warehouseList = new ArrayList<HashMap<String, String>>();
  // Tuhin
  static ArrayList<HashMap<String, String>> droneList = new ArrayList<HashMap<String, String>>();
  // Vishal
  static ArrayList<HashMap<String, String>> equipmentList = new ArrayList<HashMap<String, String>>();

  
	/**
	 *  The database file name.
	 *  
	 *  Make sure the database file is in the root folder of the project if you only provide the name and extension.
	 *  
	 *  Otherwise, you will need to provide an absolute path from your C: drive or a relative path from the folder this class is in.
	 */
	final static String DATABASE = "SemesterProject (1).db";
	
	 /**
     * Connects to the database if it exists, creates it if it does not, and returns the connection object.
     * 
     * @param databaseFileName the database file name
     * @return a connection object to the designated database
     */
    public static Connection initializeDB(String databaseFileName) {
    	/**
    	 * The "Connection String" or "Connection URL".
    	 * 
    	 * "jdbc:sqlite:" is the "subprotocol".
    	 * (If this were a SQL Server database it would be "jdbc:sqlserver:".)
    	 */
        String url = "jdbc:sqlite:" + databaseFileName;
        Connection conn = null; // If you create this variable inside the Try block it will be out of scope
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
            	// Provides some positive assurance the connection and/or creation was successful.
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The connection to the database was successful");
                
            } else {
            	// Provides some feedback in case the connection failed but did not throw an exception.
            	System.out.println("Null Connection");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("There was a problem connecting to the database.");
        }
        return conn;
    }
  
  public static void main(String[] args) throws SQLException, ParseException {
	  
	System.out.println("This is a new run");
  	Connection link = initializeDB("SemesterProject (1).db");
  	String query = "SELECT * FROM COMMUNITYMEMBER;";
  	
  	
    Scanner scan = new Scanner(System.in);
    for (int i = 0; i < 100; ++i) {

      System.out.println("Press 1 for add, 2 for delete, 3 for search, 4 for edit: ");
      int commandInput = Integer.parseInt(scan.nextLine());

      System.out.println("Press 1 for member, 2 for warehouse, 3 for equipment, 4 for drone: ");
      int entityInput = Integer.parseInt(scan.nextLine());
      

      switch (commandInput) {
        case 1:
          addEntity(entityInput, scan, link);
          break;
        case 2:
          deleteEntity(entityInput, scan);
          break;
        case 3:
          searchEntity(entityInput, scan);
          break;
        case 4:
          editEntity(entityInput, scan);
          break;
        default:
          System.out.println("INVALID ENTRY");
          break;
      }
    }
    scan.close();
  }

  private static void addEntity(int entityInput, Scanner scan, Connection link) throws SQLException, ParseException {
    switch (entityInput) {
      case 1:
        HashMap<String, String> member = Member.createMember(scan);
        memberList.add(member);
        break;
      case 2:
        HashMap<String, String> warehouse = Warehouse.createWarehouse(scan);
        warehouseList.add(warehouse);
        break;
      case 3:
        HashMap<String, String> equipment = Equipment.addEquipment(scan);
        equipmentList.add(equipment);
        break;
      case 4:
        HashMap<String, String> drone = Drones.createDrone(scan, link);
        droneList.add(drone);
        break;
      default:
        System.out.println("INVALID ENTRY");
        break;
    }
  }

  private static void deleteEntity(int entityInput, Scanner scan) {
    switch (entityInput) {
      case 1:
        Member.removeMember(scan, memberList);
        break;
      case 2:
        Warehouse.deleteWarehouse(warehouseList, scan);
        break;
      case 3:
        // equip
        Equipment.deleteEquipment(equipmentList, scan);
        break;
      case 4:
        // drone
        Drones.deleteDrone(droneList, scan);
        break;
      default:
        System.out.println("INVALID ENTRY");
        break;
    }
  }

  private static void searchEntity(int entityInput, Scanner scan) {
    // WOMP
    switch (entityInput) {
      case 1:
        HashMap<String, String> member = Member.searchMember(scan, memberList);
        System.out.println(member);
        // iterate through member and print out its attributes?
        break;
      case 2:
        HashMap<String, String> warehouse = Warehouse.searchForWarehouse(warehouseList, scan);
        System.out.println(warehouse);
        break;
      case 3:
        HashMap<String, String> equipment = Equipment.searchEquipment(equipmentList, scan);
        System.out.println(equipment);
        break;
      case 4:
        HashMap<String, String> drone = Drones.searchDrones(droneList, scan);
        System.out.println(drone);
        break;
      default:
        System.out.println("INVALID ENTRY");
        break;
    }
  }

  private static void editEntity(int entityInput, Scanner scan) {
    switch (entityInput) {
      case 1:
        Member.editMember(scan, memberList);
        break;
      case 2:
        Warehouse.editWarehouse(warehouseList, scan);
        break;
      case 3:
        Equipment.editEquipment(equipmentList, scan);
        break;
      case 4:
        Drones.editDrone(droneList, scan);
        break;
      default:
        System.out.println("INVALID ENTRY");
        break;
    }
  }
}

