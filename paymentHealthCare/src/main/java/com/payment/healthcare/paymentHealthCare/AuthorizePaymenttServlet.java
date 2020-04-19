package com.payment.healthcare.paymentHealthCare;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.base.rest.PayPalRESTException;

/**
 * Servlet implementation class AuthorizePaymenttServlet
 */
public class AuthorizePaymenttServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

   
    public AuthorizePaymenttServlet() {
       
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String appointmentId = request.getParameter("appointmentId");
		String username = request.getParameter("username");
		String tax = request.getParameter("tax");
		String subtotal = request.getParameter("subtotal");
		String amount = request.getParameter("amount");
		
		PaymentDetail paymentDetail = new PaymentDetail(appointmentId, username, tax, subtotal, amount);
		
		try {
		PaymentServices paymentServices = new PaymentServices();
		String approvalLink = paymentServices.authorizePayment(paymentDetail);
		
		response.sendRedirect(approvalLink); //Directing the client
		
		}catch(PayPalRESTException ex) {
			
			ex.printStackTrace();
			request.setAttribute("errorMessage", ex.getMessage());
			request.getRequestDispatcher("error.jsp");
			
		}
	}

}
