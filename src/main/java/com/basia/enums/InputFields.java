package com.basia.enums;

import lombok.Getter;


import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public enum InputFields {

    FIRST_NAME("First Name", "Y"),
    LAST_NAME("Last Name", "Y"),
    USERNAME("Username", "N"),
    PASSWORD("Password", "N/A"),
    EMAIL("Email", "Y"),
    ROLES("Roles", "N");

    String label;
    String isEditable;

    InputFields(String label, String isEditable) {
        this.label = label;
        this.isEditable = isEditable;
    }

    private static Predicate<InputFields> editablePredicate = inputFields -> inputFields.isEditable.equals("Y");
    private static Predicate<InputFields> notEditablePredicate = inputFields -> inputFields.isEditable.equals("N");

    public static List<String> getRegisterInputLabels() {
        return Arrays.stream(InputFields.values())
                .filter(input -> !input.equals(ROLES))
                .map(InputFields::getLabel)
                .collect(Collectors.toList());
    }

    public static List<String> getInputLabelsEnabledToEdit() {

        return filterAndGetList(editablePredicate);
    }

    public static List<String> getInputLabelsDisabledToEdit() {

        return filterAndGetList(notEditablePredicate);
    }

    private static List<String> filterAndGetList(Predicate<InputFields> predicate) {
        return Arrays.stream(InputFields.values())
                .filter(input -> !input.equals(PASSWORD))
                .filter(predicate)
                .map(InputFields::getLabel)
                .collect(Collectors.toList());
    }
}

