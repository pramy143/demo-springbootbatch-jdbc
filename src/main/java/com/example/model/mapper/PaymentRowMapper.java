package com.example.model.mapper;

import com.example.model.Payment;
import com.example.model.enums.PaymentStatus;
import com.example.model.enums.PaymentType;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRowMapper implements RowMapper<Payment> {

 @Override
 public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
  Payment payment = new Payment();
  payment.setPaymentId(rs.getLong("paymentId"));
  payment.setDescription(rs.getString("description"));
  payment.setPaymentType(PaymentType.valueOf(rs.getString("paymentType")));
  payment.setPaymentStatus(PaymentStatus.valueOf(rs.getString("paymentStatus")));
  payment.setCreatedDate(rs.getTimestamp("createdDate"));
  return payment;
 }

}