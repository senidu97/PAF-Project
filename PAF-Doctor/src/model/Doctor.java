package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Doctor {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/doca?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertDoctor(String doctorID, String fdname, String ldname, String phone, String charges, String hid,
			String uid, String speciality) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into doctor(`doctorID`,`fdname`,`ldname`,`phone`,`charges`,`hid`,`uid`,`speciality`)"
					+ " values (?, ?, ?, ?, ?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, doctorID);
			preparedStmt.setString(2, fdname);
			preparedStmt.setString(3, ldname);
			preparedStmt.setString(4, phone);
			preparedStmt.setString(5, charges);
			preparedStmt.setString(6, hid);
			preparedStmt.setString(7, uid);
			preparedStmt.setString(8, speciality);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Doctors.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readDoctors() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Doctor ID</th><th>First Name</th><th>Last Name</th><th>Phone</th><th>Charges</th><th>Hospital ID</th><th>UID</th><th>Speciality</th></tr>";
			String query = "select * from doctor";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String doctorID = Integer.toString(rs.getInt("doctorID"));
				String fdname = rs.getString("fdname");
				String ldname = rs.getString("ldname");
				String phone = Integer.toString(rs.getInt("phone"));
				String charges = Double.toString(rs.getDouble("charges"));
				String hid = rs.getString("hid");
				String uid = rs.getString("uid");
				String speciality = rs.getString("speciality");
				// Add into the html table
				output += "<tr><td>" + doctorID + "</td>";
				output += "<td>" + fdname + "</td>";
				output += "<td>" + ldname + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + charges + "</td>";
				output += "<td>" + hid + "</td>";
				output += "<td>" + uid + "</td>";
				output += "<td>" + speciality + "</td>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Doctors.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateDoctor(String doctorID, String fdname, String ldname, String phone, String charges, String hid,
			String uid, String speciality) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE doctor SET fdname=?,ldname=?,phone=?,charges=?,hid=?,uid=?,speciality=? WHERE doctorID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			
			preparedStmt.setString(1, fdname);
			preparedStmt.setString(2, ldname);
			preparedStmt.setInt(3, Integer.parseInt(phone));
			preparedStmt.setDouble(4, Double.parseDouble(charges));
			preparedStmt.setString(5, hid);
			preparedStmt.setString(6, uid);
			preparedStmt.setString(7, speciality);
			preparedStmt.setInt(8, Integer.parseInt(doctorID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteDoctor(String doctorID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from doctor where doctorID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(doctorID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Doctor.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
