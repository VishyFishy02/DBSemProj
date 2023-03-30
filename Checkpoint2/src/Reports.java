import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;



public class Reports {
    
    public static void generateReport(Scanner scan, Connection link) throws SQLException {

        String query;
        Statement stmt;
        ResultSet rslt;
        
        System.out.println("1 - Number of items rented out by a particular member");
        System.out.println("2 - Most popular item rented out");
        System.out.println("3 - Most popular manufacturer");
        System.out.println("4 - Most used drone");
        System.out.println("5 - Member that has rented out the most items");
        System.out.println("6 - Equipment by type released before a particular year");
        
        System.out.print("Enter the number of the report you would like to generate: ");
        int choice = Integer.parseInt(scan.nextLine());
        
        
        switch(choice) {
            case 1:
                System.out.println("Enter a member ID: ");
                String memID = scan.nextLine();
                query = "SELECT MemberID, COUNT(*) "
                        + "FROM CommunityMember as CM, RentalRequest as R "
                        + "WHERE CM.MemberID = R.MemberID AND CM.MemberID = \'" + memID + "\';";
                break;
            case 2:
                query = "SELECT Equipment.InventoryID, Equipment.Description, SUM(JULIANDAY(CheckIn) - JULIANDAY(CheckOut)) as CheckedOutTime, COUNT(Equipment.InventoryID) as NumCheckouts "
                        + "FROM Equipment, RentalRequest, Fees "
                        + "WHERE Equipment.InventoryID = RentalRequest.EquipmentID AND RentalRequest.RequestNo = Fees.RequestNo "
                        + "GROUP BY Equipment.InventoryID "
                        + "ORDER BY NumCheckouts DESC, CheckedOutTime DESC "
                        + "LIMIT 3; ";
                break;
            case 3:
                query = "SELECT M.Manufacturer, RR.EquipmentID, RentMoreAvg.MemberID "
                        + "FROM RentMoreAvg, RentalRequest as RR, Equipment as E, Model as M "
                        + "WHERE RentMoreAvg.MemberID = RR.MemberID AND RR.EquipmentID = E.InventoryID AND E.ModelNumber = M.ModelNumber "
                        + "GROUP BY M.Manufacturer;";
                break;
            case 4:
                query = "SELECT D.FleetID, COUNT(DISTINCT RR.RequestNo) as Deliveries, SUM(WarehouseDistance) as DistanceTraveled "
                        + "FROM Drone as D, Equipment as E, RentalRequest as RR, CommunityMember as CM "
                        + "WHERE D.FleetID = E.FleetID AND E.InventoryID = RR.EquipmentID AND RR.MemberID = CM.MemberID "
                        + "GROUP BY D.FleetID "
                        + "ORDER BY DistanceTraveled DESC, Deliveries DESC;";
                break;
            case 5:
                query = "SELECT MemberID, Name, COUNT(RequestNo) "
                        + "FROM CommunityMember as CM, RentalRequest as R, Equipment as E "
                        + "WHERE R.EquipmentID = E.InventoryID AND R.MemberID = CM.MemberID "
                        + "GROUP BY CM.MemberID "
                        + "ORDER BY COUNT(RequestNo) DESC "
                        + "LIMIT 1;";
                break;
            case 6:
                System.out.println("Enter a year: ");
                String year = scan.nextLine();
                query = "SELECT Description, EquipmentType, Year "
                        + "FROM Equipment as E, Model as M "
                        + "WHERE E.ModelNumber  = M.ModelNumber AND M.Year < " + year 
                        + " GROUP BY EquipmentType;";
                break;
             default:
                  System.out.println("Invalid input.")
                  break;
        }
        
        stmt = link.createStatement();
        rslt = stmt.executeQuery(query);
        ResultSetMetaData rsmd = rslt.getMetaData();
        int columnCount = rsmd.getColumnCount();
        
        while (rslt.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnValue = rs.getString(i);
                System.out.print(columnValue);
                if (i < columnCount) {
                    System.out.print(",  ");
                }
            }
            System.out.print("\n");
        }
      }

}
