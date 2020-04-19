package com;

import model.Appointment;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Appointments")

public class AppointmentService {
	Appointment appointmentObj = new Appointment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAppointments() {
		return appointmentObj.readAppointments();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAppointment(@FormParam("doctorID") String doctorID,
			@FormParam("appointmentNumber") String appointmentNumber,
			@FormParam("appointmentType") String appointmentType, 
			@FormParam("appointmentDate") String appointmentDate,
			@FormParam("hospitalID") String hospitalID, 
			@FormParam("patientID") String patientID,
			@FormParam("appointmentDescription") String appointmentDescription) {
		String output = appointmentObj.insertAppointment(doctorID, appointmentNumber, appointmentType, appointmentDate,
				appointmentDescription, hospitalID, patientID);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateAppointment(String appointmentData) {
		// Convert the input string to a JSON object
		JsonObject appointmentObject = new JsonParser().parse(appointmentData).getAsJsonObject();

		// Read the values from the JSON object
		String appointmentID = appointmentObject.get("appointmentID").getAsString();
		String doctorID = appointmentObject.get("doctorID").getAsString();
		String appointmentNumber = appointmentObject.get("appointmentNumber").getAsString();
		String appointmentType = appointmentObject.get("appointmentType").getAsString();
		String appointmentDate = appointmentObject.get("appointmentDate").getAsString();
		String hospitalID = appointmentObject.get("hospitalID").getAsString();
		String patientID = appointmentObject.get("patientID").getAsString();
		String appointmentDescription = appointmentObject.get("appointmentDescription").getAsString();

		String output = appointmentObj.updateAppointment(appointmentID, doctorID, appointmentNumber, appointmentType,
				appointmentDate, hospitalID, patientID, appointmentDescription);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteAppointment(String appointmentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(appointmentData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String appointmentID = doc.select("appointmentID").text();
		String output = appointmentObj.deleteAppointment(appointmentID);
		return output;
	}
}
