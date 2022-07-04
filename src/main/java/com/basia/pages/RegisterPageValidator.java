package com.basia.pages;

import com.basia.api.enums.Colors;
import com.basia.helpers.ConstValues;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

public class RegisterPageValidator extends AbstractPageValidator<RegisterPage> {

    public RegisterPageValidator(RegisterPage page) {
        super(page);
    }

    public RegisterPageValidator assertErrorMessageForFieldIsDisplayed(String label) {
        Assertions.assertThat(page.getErrorMessageForField(label).getText())
                .describedAs("Field %s should have error message but it hasn't", label)
                .contains(ConstValues.FIELD_ERROR_MESSAGE);

        return this;
    }

    public RegisterPageValidator assertFieldHasRedBorder(String label) {
        Assertions.assertThat(getBorderColor(page.getField(label)))
                .describedAs("Field %s should have red border but it hasn't", label)
                .isEqualTo(Colors.INPUT_VALIDATION_RED.getColor());

        return this;
    }

    public Color getBorderColor(WebElement element) {
        return Color.fromString(element.getCssValue("border-color"));
    }
}
