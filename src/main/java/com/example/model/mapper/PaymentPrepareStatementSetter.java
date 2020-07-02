package com.example.model.mapper;

import com.example.model.Payment;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class PaymentPrepareStatementSetter implements ItemPreparedStatementSetter<Payment> {

    @Override
    public void setValues(Payment payment, PreparedStatement sqlParamSequence) throws SQLException {

        sqlParamSequence.setString(1, payment.getPaymentType().toString());
        sqlParamSequence.setString(2, "COMPLETED");
        sqlParamSequence.setString(3, payment.getDescription());
        sqlParamSequence.setFloat(4, payment.getPaymentId());
    }
}
