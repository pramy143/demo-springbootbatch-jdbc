package com.example.model.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum PaymentStatus {
    COMPLETED("COMP"),
    REJECTED("REJ"),
    VERIFIED("VERIFIED"),
    STOPPED("STOP");

    private final String code;

    private static final Map<String, PaymentStatus> CODES;

    static {
        final Map<String, PaymentStatus> temp = new HashMap<>();

        for (final PaymentStatus type : PaymentStatus.values()) {
            temp.put(type.getCode(), type);
        }

        CODES = Collections.unmodifiableMap(temp);
    }

    PaymentStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static PaymentStatus getFromCode(String code) {
        return CODES.get(code);
    }
}
