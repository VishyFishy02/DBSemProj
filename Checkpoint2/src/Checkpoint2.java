import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
	 * The database file name.
	 * 
	 * Make sure the database file is in the root folder of the project if you only
	 * provide the name and extension.
	 * 
	 * Otherwise, you will need to provide an absolute path from your C: drive or a
	 * relative path from the folder this class is in.
	 */
	final static String DATABASE = "SemesterProject (1).db";

	/**
	 * Connects to the database if it exists, creates it if it does not, and returns
	 * the connection object.
	 * 
	 * @param databaseFileName the database file name
	 * @return a connection object to the designated database
	 */
	public static Connection initializeDB(String databaseFileName) {
		/**
		 * The "Connection String" or "Connection URL".
		 * 
		 * "jdbc:sqlite:" is the "subprotocol". (If this were a SQL Server database it
		 * would be "jdbc:sqlserver:".)
		 */
		String url = "jdbc:sqlite:" + databaseFileName;
		Connection conn = null; // If you create this variable inside the Try block it will be out of scope
		try {
			conn = DriverManager.getConnection(url);
			if (conn != null) {
				// Provides some positive assurance the connection and/or creation was
				// successful.
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("The connection to the database was successful");

			} else {
				// Provides some feedback in case the connection failed but did not throw an
				// exception.
				System.out.println("Null Connection");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("There was a problem connecting to the database.");
		}
		return conn;
	}

	/**
	 * Queries the database and prints the results.
	 * 
	 * @param conn a connection object
	 * @param sql  a SQL statement that returns rows This query is written with the
	 *             Statement class, typically used for static SQL SELECT statements
	 */
	public static void sqlQuery(Connection conn, String sql) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String value = rsmd.getColumnName(i);
				System.out.print(value);
				if (i < columnCount)
					System.out.print(",  ");
			}
			System.out.print("\n");
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rs.getString(i);
					System.out.print(columnValue);
					if (i < columnCount)
						System.out.print(",  ");
				}
				System.out.print("\n");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {

		System.out.println("This is a new run");
		Connection link = initializeDB("SemesterProject (1).db");

		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < 100; ++i) {

			System.out.println("Press 1 for add, 2 for delete, 3 for search, 4 for edit, or 5 for Useful Reports: ");
			int commandInput = Integer.parseInt(scan.nextLine());

			System.out.println("Press 1 for member, 2 for warehouse, 3 for equipment, 4 for drone: ");
			int entityInput = Integer.parseInt(scan.nextLine());

			switch (commandInput) {
			case 1:
				addEntity(entityInput, scan, link);
				break;
			case 2:
				deleteEntity(entityInput, scan, link);
				break;
			case 3:
				searchEntity(entityInput, scan, link);
				break;
			case 4:
				editEntity(entityInput, scan, link);
				break;
			case 5:
				Reports.generateReport(scan, link);
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
			Member.createMember(scan, link);
			
			break;
		case 2:
			HashMap<String, String> warehouse = Warehouse.createWarehouse(scan, link);
			warehouseList.add(warehouse);
			break;
		case 3:
			HashMap<String, String> equipment = Equipment.addEquipment(scan, link);
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

	private static void deleteEntity(int entityInput, Scanner scan, Connection link) throws SQLException {
		switch (entityInput) {
		case 1:
			Member.removeMember(scan, link);
			break;
		case 2:
			Warehouse.deleteWarehouse(warehouseList, scan, link);
			break;
		case 3:
			// equip
			Equipment.deleteEquipment(equipmentList, scan, link);
			break;
		case 4:
			// drone
			Drones.deleteDrone(droneList, scan, link);
			break;
		default:
			System.out.println("INVALID ENTRY");
			break;
		}
	}

	private static void searchEntity(int entityInput, Scanner scan, Connection link) throws SQLException {
		// WOMP
		switch (entityInput) {
		case 1:
			ResultSet member = Member.searchMember(scan, link);
			int columnCount = member.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnValue = member.getString(i);
				System.out.print(columnValue);
				if (i < columnCount)
					System.out.print(",  ");
			}
			System.out.print("\n");
			break;
		case 2:
			ResultSet warehouse = Warehouse.searchForWarehouse(warehouseList, scan, link);
			int columnCount2 = warehouse.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount2; i++) {
				String columnValue = warehouse.getString(i);
				System.out.print(columnValue);
				if (i < columnCount2)
					System.out.print(",  ");
			}
			System.out.print("\n");
			break;
		case 3:
			ResultSet equipment = Equipment.searchEquipment(equipmentList, scan, link);
			System.out.println(equipment);
			break;
		case 4:
			ResultSet drone = Drones.searchDrones(droneList, scan, link);
			int columnCount4 = drone.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount4; i++) {
				String columnValue = drone.getString(i);
				System.out.print(columnValue);
				if (i < columnCount4)
					System.out.print(",  ");
			}
			System.out.print("\n");
			break;
		default:
			System.out.println("INVALID ENTRY");
			break;
		}
	}

	private static void editEntity(int entityInput, Scanner scan, Connection link) throws SQLException {
		switch (entityInput) {
		case 1:
			Member.editMember(scan, link);
			break;
		case 2:
			Warehouse.editWarehouse(warehouseList, scan, link);
			break;
		case 3:
			Equipment.editEquipment(equipmentList, scan, link);
			break;
		case 4:
			Drones.editDrone(droneList, scan, link);
			break;
		default:
			System.out.println("INVALID ENTRY");
			break;
		}
	}
}
