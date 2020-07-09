package com.example.camel.processor;

import com.example.camel.dao.PersistPaymentProcessingDaoImpl;
import com.example.model.Payment;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

@Component
public class PaymentProcessingOutProcessor implements Processor {

	@Autowired
	private PersistPaymentProcessingDaoImpl persistPaymentProcessingDaoImpl;

	@Resource
	DataSource dataMysqlSource;


	public void process(Exchange exchange) throws Exception {
		Payment payment = exchange.getIn().getBody(Payment.class);
		persistPaymentProcessingDaoImpl.savePayment(payment);
		exchange.getIn().setBody(payment);

	}

	public PersistPaymentProcessingDaoImpl getPersistPaymentProcessingDaoImpl() {
		return persistPaymentProcessingDaoImpl;
	}

	public void setPersistPaymentProcessingDaoImpl(final PersistPaymentProcessingDaoImpl persistPaymentProcessingDaoImpl) {
		this.persistPaymentProcessingDaoImpl = persistPaymentProcessingDaoImpl;
	}

	public DataSource getDataMysqlSource() {
		return dataMysqlSource;
	}

	public void setDataMysqlSource(final DataSource dataMysqlSource) {
		this.dataMysqlSource = dataMysqlSource;
	}
}