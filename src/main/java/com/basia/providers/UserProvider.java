package com.basia.providers;

import com.basia.api.dto.RegisterDto;
import com.basia.api.dto.Roles;
import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProvider {

    private static final Faker FAKER = new Faker();

    public static RegisterDto getRandomUser() {
        return RegisterDto.builder()
                .firstName(FAKER.name().firstName() + FAKER.random().hex(1))
                .lastName(FAKER.name().lastName() + FAKER.random().hex(1))
                .password(FAKER.internet().password())
                .roles(Roles.values())
                .email(FAKER.internet().emailAddress())
                .username(FAKER.name().username() + FAKER.random().hex(1))
                .build();
    }

    public static RegisterDto getUserWithTooShortData() {
        return RegisterDto.builder()
                .firstName(RandomStringUtils.randomAlphabetic(3))
                .lastName(RandomStringUtils.randomAlphabetic(3))
                .password(RandomStringUtils.randomAlphabetic(3))
                .roles(new Roles[]{Roles.ROLE_CLIENT})
                .email(RandomStringUtils.randomAlphabetic(3))
                .username(RandomStringUtils.randomAlphabetic(3))
                .build();
    }
}
