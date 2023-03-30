/*Member attributes: UserID, Rental History, Warehouse Distance, Active status, Name, Email, Phone, Start Address*/

import java.util.HashMap;

import java.util.Scanner;

import java.util.ArrayList;

import java.sql.Connection;

import java.sql.DatabaseMetaData;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;

import java.sql.SQLException;

import java.sql.Statement;

import java.text.ParseException;

import java.text.SimpleDateFormat;

public class Member {

	public static void createMember(Scanner scan, Connection link) throws SQLException, ParseException {

		String[] attributes = { "UserID", "Active Status (0 for false, 1 for true)", "Name", "Start Address", "Phone",
				"Email",

				"Today's Date", "Warehouse Distance", "Warehouse Address" };

		String[] inputs = new String[attributes.length];

		for (int i = 0; i < inputs.length; i++) {

			System.out.println("Enter attribute value for " + attributes[i] + ": ");

			inputs[i] = scan.nextLine();

		}

		String query = "insert into COMMUNITYMEMBER(MemberID, ActiveStatus, Name, Address, PhoneNumber, Email, "

				+ "StartDate, WarehouseDistance, WarehouseAddress) "

				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement stmt = link.prepareStatement(query);

		stmt.setInt(1, Integer.parseInt(inputs[0]));

		stmt.setInt(2, Integer.parseInt(inputs[1]));

		stmt.setString(3, inputs[2]);

		stmt.setString(4, inputs[3]);

		stmt.setString(5, inputs[4]);

		stmt.setString(6, inputs[5]);

		java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(inputs[6]);

		java.sql.Date todaysDate = new java.sql.Date(utilDate.getTime());

		stmt.setDate(7, todaysDate);

		stmt.setInt(8, Integer.parseInt(inputs[7]));

		stmt.setString(9, inputs[8]);

		stmt.executeUpdate();

		System.out.println("***New member inserted***");

	}

	public static ResultSet searchMember(Scanner scan, Connection link) throws SQLException {

		System.out.println("Enter UserID to search for: ");

		String value = scan.nextLine();

		String query = "SELECT COMMUNITYMEMBER.MemberID FROM COMMUNITYMEMBER WHERE COMMUNITYMEMBER.MemberID = " + value;

		PreparedStatement stmt = link.prepareStatement(query);

		return stmt.executeQuery();

	}

	public static void editMember(Scanner scan, Connection link) throws SQLException {

		String[] attributes = { "UserID", "Rental History", "Warehouse Distance", "Active Status", "Name", "Email",
				"Phone", "Start Address" };

		ResultSet result = searchMember(scan, link);

		if (result == null)
			System.out.println("INVALID ENTRY");

		else {

			int memberID = result.getInt("MemberID");

			for (int i = 0; i < attributes.length; i++) {

				System.out.println(i + ": " + attributes[i]);

			}

			System.out.println("Select the number of the attribute to edit: ");

			String attr = attributes[Integer.parseInt(scan.nextLine())];

			System.out.println("Enter new value for the attribute: ");

			String value = scan.nextLine();

			String query = "UPDATE COMMUNITYMEMBER SET " + attr + "= \'" + value
					+ "\' WHERE COMMUNITYMEMBER.MemberID = "

					+ memberID;

			Statement stmt = link.createStatement();

			stmt.executeUpdate(query);

			System.out.println("***Record successfully edited***");

		}

	}

	public static void removeMember(Scanner scan, Connection link) throws SQLException {

		ResultSet removeIt = searchMember(scan, link);

		int memberID = removeIt.getInt("MemberID");

		if (removeIt != null) {

			String query = "DELETE FROM COMMUNITYMEMBER WHERE COMMUNITYMEMBER.MemberID = " + memberID;

			Statement stmt = link.createStatement();

			stmt.executeUpdate(query);

		}

	}

}