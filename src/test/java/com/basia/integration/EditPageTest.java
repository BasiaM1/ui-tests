package com.basia.integration;

import com.basia.api.ApiDeleteUser;
import com.basia.api.ApiUserDetails;
import com.basia.api.dto.register.RegisterDto;
import com.basia.api.dto.userdetails.UserDetailsDto;
import com.basia.config.YamlParser;
import com.basia.enums.InputFields;
import com.basia.pages.EditPage;
import com.basia.pages.EditPageValidator;
import com.basia.pages.HomePage;
import com.basia.utils.LoginUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.basia.enums.InputFields.FIRST_NAME;
import static com.basia.enums.InputFields.LAST_NAME;
import static com.basia.providers.UserProvider.getRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

public class EditPageTest extends BaseTest {

    private final RegisterDto user = getRandomUser();
    @Autowired
    private ApiUserDetails apiUserDetails;
    @Autowired
    private ApiDeleteUser apiDeleteUser;
    @Autowired
    private LoginUtil loginUtil;
    private String token;

    @BeforeMethod
    private void goToHomePageAsRandomUser() {
        token = loginUtil.loginAsRandomUser(user, driver);
    }

    @AfterMethod
    public void cleanUp() {
        apiDeleteUser.deleteUser(user.getUsername(), token);
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

        String newFirstName = RandomStringUtils.randomAlphabetic(8);
        String newLastName = RandomStringUtils.randomAlphabetic(8);
        editPage.editUserDetails(FIRST_NAME.getLabel(), newFirstName);
        editPage.editUserDetails(LAST_NAME.getLabel(), newLastName);

        editPage.getEditUserButton().click();

        UserDetailsDto editedUserDetails = apiUserDetails.getUserDetails(user.getUsername(), token);
        assertThat(editedUserDetails.getFirstName()).isEqualTo(newFirstName);
        assertThat(editedUserDetails.getLastName()).isEqualTo(newLastName);
    }
}
