package com;

import model.Doctor;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Doctors")

public class DoctorService {
	Doctor doctorObj = new Doctor();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readDoctors() {
		return doctorObj.readDoctors();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertDoctor(@FormParam("doctorID") String doctorID, @FormParam("fdname") String fdname,
			@FormParam("ldname") String ldname, @FormParam("phone") String phone, @FormParam("charges") String charges,
			@FormParam("hid") String hid, @FormParam("uid") String uid, @FormParam("speciality") String speciality) {
		String output = doctorObj.insertDoctor(doctorID, fdname, ldname, phone, charges, hid, uid, speciality);
		return output;

	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public String updateDoctor(String doctorData) {
		// Convert the input string to a JSON object
		JsonObject DoctorObject = new JsonParser().parse(doctorData).getAsJsonObject();

		// Read the values from the JSON object
		String doctorID = DoctorObject.get("doctorID").getAsString();
		String fdname = DoctorObject.get("fdname").getAsString();
		String ldname = DoctorObject.get("ldname").getAsString();
		String phone = DoctorObject.get("phone").getAsString();
		String charges = DoctorObject.get("charges").getAsString();
		String hid = DoctorObject.get("hid").getAsString();
		String uid = DoctorObject.get("uid").getAsString();
		String speciality = DoctorObject.get("speciality").getAsString();

		String output = doctorObj.updateDoctor(doctorID, fdname, ldname, phone, charges, hid, uid, speciality);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteDoctor(String doctorData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(doctorData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String doctorID = doc.select("doctorID").text();
		String output = doctorObj.deleteDoctor(doctorID);
		return output;
	}
}
