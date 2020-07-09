package com.example.camel.route;

import com.example.camel.dao.PersistPaymentProcessingDaoImpl;
import com.example.camel.processor.PaymentProcessingOutProcessor;
import com.example.camel.processor.PaymentProcessingProcessor;
import com.example.model.Payment;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;

@Component
public class PaymentHubProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // XML Data Format
        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        JAXBContext con = JAXBContext.newInstance(Payment.class);
        xmlDataFormat.setContext(con);

        // JSON Data Format
        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Payment.class);

        from("file:C:/inputFolder").startupOrder(1)
                                   .autoStartup(true).doTry().unmarshal(xmlDataFormat).
            process(new PaymentProcessingProcessor()).
                                       to("jms:queue:inProcessingQueue").doCatch(Exception.class).process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                System.out.println(cause);
            }
        });

        from("jms:queue:inProcessingQueue")
            .routeId("inQueue")
            .startupOrder(2)
            .autoStartup(true)
            .doTry()
            .unmarshal(xmlDataFormat)
            .process(new PaymentProcessingProcessor())
            //.marshal(jsonDataFormat)
            .to("jms:queue:outProcessingQueue")
            .marshal(xmlDataFormat)
            .process(new Processor() {
                public void process(Exchange exchange) throws Exception {
                    Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    System.out.println(cause);
                }
            })
        .end();

            from("jms:queue:outProcessingQueue")
                .routeId("outQueue")
                .startupOrder(3)
                .autoStartup(true)
            .doTry()
            .unmarshal(xmlDataFormat)
            .process(new PaymentProcessingOutProcessor())
            .bean(PersistPaymentProcessingDaoImpl.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        System.out.println(cause);
                    }
                })
                .end();
    }
}
