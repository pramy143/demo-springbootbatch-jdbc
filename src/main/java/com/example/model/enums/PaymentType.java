package com.example.model.enums;

import com.example.model.EnumUtil;

import java.util.List;

public enum PaymentType implements EnumUtil<PaymentType> {
    SEPA,
    INTERNATIONAL,
    BANK_TO_BANK,
    FREE_POSTING,
    CHARGE_ENGINE_PAYMENT;

    public static List<PaymentType> paymentTypes(){
        return List.of(
            SEPA,
            INTERNATIONAL,
            BANK_TO_BANK,
            FREE_POSTING,
            CHARGE_ENGINE_PAYMENT
        );
    }


}
