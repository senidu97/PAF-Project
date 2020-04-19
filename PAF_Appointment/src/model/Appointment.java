package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/PAF-Project?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertAppointment(String doctorID,String appointmentNumber,String type,String date,String description,String hospitalID,String patientID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into appointment(`doctorID`,`appointmentNumber`,`appointmentType`,`appointmentDate`,`appointmentDescription`,`hospitalID`,`patientID`)"
					+ " values (?, ?, ?, ?, ?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, doctorID);
			preparedStmt.setString(2, appointmentNumber);
			preparedStmt.setString(3, type);
			preparedStmt.setDate(4,Date.valueOf(date));
			preparedStmt.setString(5, description);
			preparedStmt.setString(6, hospitalID);
			preparedStmt.setString(7, patientID);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the Appointments.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readAppointments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Docto ID</th><th>Appointment Number</th><th>Appointment Type</th><th>Appointment Date</th><th>Appointment Description</th><th>Hospital ID</th><th>Patient ID</th></tr>";
			String query = "select * from appointment";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String appointmentID = Integer.toString(rs.getInt("appointmentID"));
				String doctorID = Integer.toString(rs.getInt("doctorID"));
				String appointmentNumber = Integer.toString(rs.getInt("appointmentNumber"));
				String appointmentType = rs.getString("appointmentType");
				String appointmentDate = rs.getString("appointmentDate");
				String appointmentDescription = rs.getString("appointmentDescription");
				String hospitalID = Integer.toString(rs.getInt("hospitalID"));
				String patientID = Integer.toString(rs.getInt("patientID"));
				// Add into the html table
				output += "<tr><td>" + doctorID + "</td>";
				output += "<td>" + appointmentNumber + "</td>";
				output += "<td>" + appointmentType + "</td>";
				output += "<td>" +appointmentDate + "</td>";
				output += "<td>" + appointmentDescription + "</td>";
				output += "<td>" + hospitalID + "</td>";
				output += "<td>" + patientID + "</td>";
				// buttons
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Appointments.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateAppointment(String appointmentID,String doctorID,String appointmentNumber,String appointmentType,String appointmentDate,String hospitalID,String patientID,String appointmentDescription) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE appointment SET doctorID=?,appointmentNumber=?,appointmentType=?,appointmentDate=?,appointmentDescription=?,hospitalID=?,patientID=? WHERE appointmentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(doctorID));
			preparedStmt.setInt(2, Integer.parseInt(appointmentNumber));
			preparedStmt.setString(3, appointmentType);
			preparedStmt.setDate(4,Date.valueOf(appointmentDate));
			preparedStmt.setString(5, appointmentDescription);
			preparedStmt.setInt(6, Integer.parseInt(hospitalID));
			preparedStmt.setInt(7, Integer.parseInt(patientID));
			
			preparedStmt.setInt(8, Integer.parseInt(appointmentID));

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
	
	public String deleteAppointment(String appointmentID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from appointment where appointmentID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(appointmentID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the Appointment.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
