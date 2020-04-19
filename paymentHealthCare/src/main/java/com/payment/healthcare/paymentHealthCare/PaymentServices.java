package com.payment.healthcare.paymentHealthCare;



import java.util.ArrayList;
import java.util.List;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class PaymentServices {
	
//	this class acts as a layer between java servlets and paypal server
//	setting up the paypal sdk
	
	private static final String CLIENT_ID = "ATd9IhBksDZum5q9MoWic3QYAShwiVhP1NIACXeWpLcIYo4qnLit9XJWhl3DY0_vIWii9PJMeXzc1KeH";
    private static final String CLIENT_SECRET = "EM_nts-SA3jgr7TLHainQdCI2D04Ou2C5RRZPYf8U8696XpZjSn7CdPwXBg4xJbLI9HXRnYDNV2refUJ";
    private static final String MODE = "sandbox";
    
    
    public String authorizePayment(PaymentDetail paymentDetail)throws PayPalRESTException {
    	
    	Payer payer = getPayerInformation();
    	RedirectUrls redirecturls = getRedirectURLs();
    	List<Transaction> listTransaction = getTransactionInformation(paymentDetail);
    	
    	Payment requestPayment = new Payment();
    	requestPayment.setTransactions(listTransaction)
    				  .setRedirectUrls(redirecturls)
    				  .setPayer(payer)
    				  .setIntent("authorize"); //purpose of the payment request is to authorize
    	
    	APIContext apiContext = new APIContext(CLIENT_ID,CLIENT_SECRET, MODE);
    	
    	Payment approvedPayment = requestPayment.create(apiContext);
    	
    	System.out.print(approvedPayment);
    	
    	return getApprovalLink(approvedPayment);
    	
    	
    }
    private String getApprovalLink(Payment approvedPayment) {
    	
    	List<Links> links = approvedPayment.getLinks();
    	
    	String approvalLink = null;
    	for(Links link: links) {
    		
    		if(link.getRel().equalsIgnoreCase("approval_url")) 
    		approvalLink = link.getHref();
    	}
    	
    	return approvalLink;
    }
    
    private List<Transaction> getTransactionInformation(PaymentDetail paymentDetail){
    	
    	//setting up transaction details which should be sent to paypal
    	Details detail = new Details();
    	detail.setSubtotal(paymentDetail.getSubtotal());
    	detail.setTax(paymentDetail.getTax());
    	
    	Amount amount = new Amount();
    	amount.setCurrency("LKR");
    	amount.setTotal(paymentDetail.getAmount());
    	
    	Transaction transaction = new Transaction();
    	transaction.setAmount(amount);
    	transaction.setDescription(paymentDetail.getAppointmentId());
    	
    	ItemList itemList = new ItemList();
    	List<Item> items = new ArrayList<Item>();
    	
    	Item item = new Item();
    	item.setCurrency("LKR")
    		.setName(paymentDetail.getAppointmentId()) //description feature in paypal
    		.setPrice(paymentDetail.getSubtotal()) //setting up the fee
    		.setTax(paymentDetail.getTax())
    		.setQuantity("1");
    	
    	items.add(item);
    	itemList.setItems(items);
    	transaction.setItemList(itemList);
    	
    	List<Transaction> listTransaction = new ArrayList<Transaction>();
    	listTransaction.add(transaction);
    	
    		
    	
    	
    	return listTransaction;
    }
    


	private RedirectUrls getRedirectURLs() {
		RedirectUrls redirecturls = new RedirectUrls();
    	redirecturls.setCancelUrl("http://localhost:8083/paymentHealthCare/cancel.html"); //if the payer needs to cancel a payment
    	redirecturls.setReturnUrl("http://localhost:8083/paymentHealthCare/review_payment"); //if the payer needs to proceed
    	
    	return redirecturls;
	}
	
	public Payment getPaymentDetails(String paymentId)throws PayPalRESTException {
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		 return Payment.get(apiContext, paymentId);
	} 
	
	public Payment executePayment(String paymentId, String payerId)throws PayPalRESTException {
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution = setPayerId(payerId);
		
		Payment payment = new Payment().setId(paymentId);
		
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		
		return payment.execute(apiContext, paymentExecution);
	}
	

	private Payer getPayerInformation() {
		Payer payer = new Payer();
    	payer.setPaymentMethod("paypal");
    	
    	PayerInfo payerInfo = new PayerInfo();
    	payerInfo.setFirstName("Senidu")
    			 .setLastName("Mendis")
    			 .setEmail("senidumendis@gmail.com"); //payers info
    	
    	payer.setPayerInfo(payerInfo);
    	return payer;
	}

}
