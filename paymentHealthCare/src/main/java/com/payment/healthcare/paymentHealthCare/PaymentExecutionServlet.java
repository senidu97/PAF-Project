package com.payment.healthcare.paymentHealthCare;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

/**
 * Servlet implementation class PaymentExecutionServlet
 */
public class PaymentExecutionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public PaymentExecutionServlet() {
        super();
      
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String paymentId = request.getParameter("paymentId");
		String payerId = request.getParameter("PayerID");
		
		try {
		PaymentServices paymentServices = new PaymentServices();
		Payment payment = paymentServices.executePayment(paymentId, payerId);
		
		PayerInfo payerInfo = payment.getPayer().getPayerInfo();
		Transaction transaction = payment.getTransactions().get(0);
		
		
		request.setAttribute("payer", payerInfo);
		request.setAttribute("transaction", transaction);
		
		request.getRequestDispatcher("reciept.jsp").foward(request,response);
		}catch(PayPalRESTException ex) {
			
			ex.printStackTrace();
			request.setAttribute("errorMessage", "Could not execute the payment");
			request.getRequestDispatcher("error.jsp").forward(request, response);
			
		}
	}

}
