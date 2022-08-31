package com.basia.pages;

import com.basia.enums.Colors;
import com.basia.helpers.ConstValues;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterPageValidator extends AbstractPageValidator<RegisterPage> {

    public RegisterPageValidator(RegisterPage page) {
        super(page);
    }

    public RegisterPageValidator assertErrorMessageForFieldIsDisplayed(String label) {
        assertThat(page.getErrorMessageForField(label).getText())
                .describedAs("Field %s should have error message but it hasn't", label)
                .contains(ConstValues.FIELD_ERROR_MESSAGE);

        return this;
    }

    public RegisterPageValidator assertFieldHasRedBorder(String label) {
        assertThat(getBorderColor(page.getField(label)))
                .describedAs("Field %s should have red border but it hasn't", label)
                .isEqualTo(Colors.INPUT_VALIDATION_RED.getColor());

        return this;
    }

    public Color getBorderColor(WebElement element) {
        return Color.fromString(element.getCssValue("border-color"));
    }
}
