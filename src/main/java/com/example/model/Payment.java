package com.example.model;

import com.example.model.enums.PaymentStatus;
import com.example.model.enums.PaymentType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement(name = "payments")
@XmlAccessorType(XmlAccessType.FIELD)
public class Payment {

    private Long paymentId;

    private PaymentType paymentType;

    private PaymentStatus paymentStatus;

    private String description;

    private Date createdDate;

    public Payment() {}

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.description = builder.description;
        this.paymentType = builder.paymentType;
        this.paymentStatus = builder.paymentStatus;

    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final Long paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(final PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public static final class Builder {
        private Long paymentId;
        private String description;
        private PaymentType paymentType;
        private PaymentStatus paymentStatus;

        public Builder paymentId(Long paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder paymentType(PaymentType paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        public Builder paymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder clone(Payment payment) {
            this.paymentId = payment.getPaymentId();
            this.paymentType = payment.getPaymentType();
            this.paymentStatus = payment.getPaymentStatus();

            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
