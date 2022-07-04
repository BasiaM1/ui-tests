package com.basia.api.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum RegisterFields {

    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    USERNAME("Username"),
    PASSWORD("Password"),
    EMAIL("Email");

    String label;

    RegisterFields(String label) {
        this.label = label;
    }

    public static List<String> getRegisterFieldsLabels() {
        return Arrays.stream(RegisterFields.values())
                .map(RegisterFields::getLabel)
                .collect(Collectors.toList());
    }
}

