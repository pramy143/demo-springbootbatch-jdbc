package com.example.listener;

import com.example.model.Payment;
import com.example.model.enums.PaymentStatus;
import com.example.model.enums.PaymentType;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PaymentProcessingJobExecutionListener extends NamedParameterJdbcDaoSupport implements JobExecutionListener {

	private NamedParameterJdbcTemplate jdbcTemplate;

	PaymentProcessingJobExecutionListener (DataSource dataSource) {
		setDataSource(dataSource);
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
	    System.out.println("Executing job id " + jobExecution.getId());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
	    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
	        List<Payment> result = jdbcTemplate.query("SELECT * FROM payments",
                                                      new RowMapper<Payment>() {
	            @Override
	            public Payment mapRow(ResultSet rs, int row) throws SQLException {
	                return new Payment.Builder()
						.paymentId(rs.getLong(1))
						.paymentType(PaymentType.valueOf(rs.getString(3)))
						.paymentStatus(PaymentStatus.valueOf(rs.getString(4)))
						.description(rs.getString(2)).build();
	            }
	        });
	        System.out.println("Number of Records:"+result.size());
	    }
	}
}