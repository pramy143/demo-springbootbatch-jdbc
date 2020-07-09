package com.example.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.sql.DataSource;

@Configuration
@EnableJms
public class ReceiverConfig {

  @Bean
  public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory =
        new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");

    return activeMQConnectionFactory;
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory =
        new DefaultJmsListenerContainerFactory();
    factory
        .setConnectionFactory(receiverActiveMQConnectionFactory());

    return factory;
  }

  @Bean(name = "dataMysqlSource")
  public DataSource dataMysqlSource() {
    final DriverManagerDataSource dataMysqlSource = new DriverManagerDataSource();
    dataMysqlSource.setSchema("payments");
    dataMysqlSource.setUrl("jdbc:mysql://localhost:3306/payments_hub?useSSL=false");
    dataMysqlSource.setUsername("payment");
    dataMysqlSource.setPassword("payment");

    return dataMysqlSource;
  }

}