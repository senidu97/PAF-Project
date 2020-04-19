package com.payment.healthcare.paymentHealthCare;


public class PaymentDetail {
	
	public String appointmentId;
	public String username;
	public float tax;
	public float amount;
	public float subtotal;
	
	
	
	
	/*getter methods return String for currency values because PayPal API requires the amount in String.*/
	

	
	public PaymentDetail(String appointmentId, String username, String tax, String subtotal, String amount) {
		super();
		this.appointmentId = appointmentId;
		this.subtotal = Float.parseFloat(subtotal);
		this.amount = Float.parseFloat(amount);
		this.tax = Float.parseFloat(tax);
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	
	public String getAppointmentId() {
		return appointmentId;
	}

	public String getTax() {
		return String.format("%.2f", tax);
	}

	public String getAmount() {
		return String.format("%.2f", amount);
	}

	public String getSubtotal() {
		return String.format("%.2f", subtotal);
	}

	

	
	
	

}
