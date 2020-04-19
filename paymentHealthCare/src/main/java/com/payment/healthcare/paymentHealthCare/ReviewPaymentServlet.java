package com.payment.healthcare.paymentHealthCare;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.api.payments.Transaction;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Servlet implementation class ReviewPaymentServlet
 */
public class ReviewPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ReviewPaymentServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		try {
		PaymentServices paymentServices = new PaymentServices();
		Payment payment = paymentServices.getPaymentDetails(paymentId);
		
		PayerInfo payerInfo = payment.getPayer().getPayerInfo();
		Transaction transaction = payment.getTransactions().get(0);
		
		
		request.setAttribute("payer", payerInfo);
		request.setAttribute("transaction", transaction);
		
		
		String url = "review.jsp?paymentId="+paymentId+"&PayerID="+payerId; //Redirecting to the review page
		request.getRequestDispatcher(url).foward(request, response);
		}catch(PayPalRESTException ex) {
			
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Could not get payment details");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			
		}
	}

}
