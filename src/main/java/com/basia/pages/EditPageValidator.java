package com.basia.pages;

import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.register.Roles;
import com.basia.api.enums.InputFields;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.basia.api.enums.InputFields.*;
import static org.assertj.core.api.Assertions.*;

public class EditPageValidator extends AbstractPageValidator<EditPage> {

    public EditPageValidator(EditPage page) {
        super(page);
    }

    public void assertFieldsAreDisabledToEdit(String label) {
        assertThat(page.inputField(label).isEnabled())
                .describedAs("Checking if field %s is disabled", label)
                .isFalse();
    }

    public void assertFieldsAreEnabledToEdit(String label) {
        assertThat(page.inputField(label).isEnabled())
                .describedAs("Checking if field %s is disabled", label)
                .isTrue();
    }

    public void assertDataInEditFormAreCorrect(RegisterDto user) {
        assertThat(page.inputField(FIRST_NAME.getLabel()).getDomProperty("value")).isEqualTo(user.getFirstName());
        assertThat(page.inputField(LAST_NAME.getLabel()).getDomProperty("value")).isEqualTo(user.getLastName());
        assertThat(page.inputField(EMAIL.getLabel()).getDomProperty("value")).isEqualTo(user.getEmail());
        assertThat(page.inputField(USERNAME.getLabel()).getDomProperty("value")).isEqualTo(user.getUsername());
        assertThat(page.inputField(ROLES.getLabel()).getDomProperty("value")).isEqualTo(buildExpectedString(user.getRoles()));



    }

    private String buildExpectedString(Roles[] roles) {
       return Arrays.stream(roles)
                .map(Enum::toString)
                .collect(Collectors.joining(","));
    }
}
