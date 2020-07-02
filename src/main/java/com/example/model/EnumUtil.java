package com.example.model;

import com.google.common.base.Enums;

import java.util.List;

public interface EnumUtil<T extends Enum<?>> {

    static <E extends Enum<E>> E from(Class<E> enumClass, E enumType) {
        return from(enumClass, enumType.name());
    }

    static <E extends Enum<E>> E from(Class<E> enumClass, String value) {
        return from(enumClass, value, null);
    }

    static <E extends Enum<E>> E from(Class<E> enumClass, String value, E defaultEnumValue) {
        if(value == null) {
            return defaultEnumValue;
        }

        if(defaultEnumValue == null) {
            return Enums.getIfPresent(enumClass, value).orNull();
        }

        return Enums.getIfPresent(enumClass, value).or(defaultEnumValue);
    }

    default boolean in(List<T> listOfEnumValues) {
        if(listOfEnumValues != null) {
            for (T enumValue : listOfEnumValues) {
                if(enumValue == this) {
                    return true;
                }
            }
        }

        return false;
    }

    default boolean in(T... listOfEnumValues) {
        if(listOfEnumValues != null) {
            for (T enumValue : listOfEnumValues) {
                if(enumValue == this) {
                    return true;
                }
            }
        }

        return false;
    }


    default boolean notIn(List<T> listOfEnumValues) {
        return !in(listOfEnumValues);
    }

    default boolean notIn(T... listOfEnumValues) {
        return !in(listOfEnumValues);
    }
}