package com.example.camel.processor;

import com.example.model.Payment;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PaymentProcessingProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Payment payment = exchange.getIn().getBody(Payment.class);
		exchange.getIn().setBody(payment);
	}

}