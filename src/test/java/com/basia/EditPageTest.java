package com.basia;

import com.basia.api.dto.register.RegisterDto;
import com.basia.api.enums.InputFields;
import com.basia.config.YamlParser;
import com.basia.pages.EditPage;
import com.basia.pages.EditPageValidator;
import com.basia.pages.HomePage;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.basia.api.enums.InputFields.FIRST_NAME;
import static com.basia.api.enums.InputFields.LAST_NAME;
import static com.basia.providers.UserProvider.getRandomUser;
import static com.basia.utils.LoginUtil.loginAsRandomUser;

public class EditPageTest extends BaseTest {

    private RegisterDto user = getRandomUser();
    private String token;

    @BeforeMethod
    private void goToHomePageAsRandomUser() {
        token = loginAsRandomUser(user, driver);
    }

    @SneakyThrows
    @Test
    public void shouldBeAbleToSeeCorrectEditPage() {
        driver.navigate().to(YamlParser.getConfig().getUrl());

        new HomePage(driver)
                .goToEditUserDetails(user.getFirstName(), user.getLastName());
        EditPage editPage = new EditPage(driver);
        EditPageValidator validator = new EditPageValidator(editPage);

        InputFields.getInputLabelsDisabledToEdit().forEach(validator::assertFieldsAreDisabledToEdit);
        InputFields.getInputLabelsEnabledToEdit().forEach(validator::assertFieldsAreEnabledToEdit);
        validator.assertDataInEditFormAreCorrect(user);

        editPage.editUserDetails(FIRST_NAME.getLabel(), RandomStringUtils.randomAlphabetic(8));
        editPage.editUserDetails(LAST_NAME.getLabel(), RandomStringUtils.randomAlphabetic(8));

        editPage.getEditUserButton().click();
    }
}
