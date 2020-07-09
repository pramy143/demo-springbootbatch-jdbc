package com.example.camel.dao;

import com.example.model.Payment;
import com.example.model.mapper.PaymentRowMapper;

import org.apache.camel.Handler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional
public class PersistPaymentProcessingDaoImpl extends NamedParameterJdbcDaoSupport {

    @Resource
    private DataSource dataMysqlSource;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public PersistPaymentProcessingDaoImpl(DataSource dataMysqlSource){
        setDataSource(dataMysqlSource);
        jdbcTemplate = new NamedParameterJdbcTemplate(dataMysqlSource);
    }



    @Handler
    public void savePayment(Payment payment){

        this.createPayment(payment);
    }

    public Payment createPayment(final Payment payment) {
        final String insertQuery= "INSERT INTO " +
                                  "payments(" +
                                  "description, " +
                                  "paymentType, " +
                                  "paymentStatus, " +
                                  "createdDate) " +
                                  "VALUES (:description, " +
                                  ":paymentType, " +
                                  ":paymentStatus, " +
                                  ":createdDate" +
                                  ")";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertQuery, createSqlParameterSourceForInsert(payment), keyHolder);

        return fetchPaymentById(keyHolder.getKey().longValue());
    }

    private SqlParameterSource createSqlParameterSourceForInsert(final Payment payment) {
        return new MapSqlParameterSource("paymentStatus", payment.getPaymentStatus())
            .addValue("paymentType", payment.getPaymentType())
            .addValue("description", payment.getDescription())
            .addValue("createdDate", new Date());
    }

    @Transactional
    private Payment fetchPaymentById(final Long paymentId) {
        final String selectByIdQuery= "SELECT * from payments where paymentId = :paymentId";
        Map<String, Object> params = new HashMap<>();
        params.put("paymentId", paymentId);

        return jdbcTemplate.queryForObject(selectByIdQuery, params, new PaymentRowMapper());
    }
}
