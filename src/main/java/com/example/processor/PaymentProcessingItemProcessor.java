package com.example.processor;

import com.example.model.Payment;

import org.springframework.batch.item.ItemProcessor;

public class PaymentProcessingItemProcessor implements ItemProcessor<Payment, Payment> {

 @Override
 public Payment process(Payment payment) throws Exception {
  return payment;
 }

}