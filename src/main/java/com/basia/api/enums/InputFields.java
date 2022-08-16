package com.basia.api.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum InputFields {

    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    USERNAME("Username"),
    PASSWORD("Password"),
    EMAIL("Email"),
    ROLES("Roles");

    String label;

    InputFields(String label) {
        this.label = label;
    }

    public static List<String> getRegisterInputLabels() {
        return Arrays.stream(InputFields.values())
                .filter(input -> !input.equals(ROLES))
                .map(InputFields::getLabel)
                .collect(Collectors.toList());
    }

    public static List<String> getAllEditInputLabels() {
        return Arrays.stream(InputFields.values())
                .filter(input -> !input.equals(PASSWORD))
                .map(InputFields::getLabel)
                .collect(Collectors.toList());
    }

    public static List<String> getInputLabelsEnabledToEdit() {
        return Arrays.asList(FIRST_NAME.label, LAST_NAME.label, EMAIL.label);
    }

    public static List<String> getInputLabelsDisabledToEdit() {
        return Arrays.asList(USERNAME.label, ROLES.label);
    }
}

