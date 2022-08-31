package com.basia.enums;

import lombok.Getter;
import org.openqa.selenium.support.Color;

@Getter
public enum Colors {

    INPUT_VALIDATION_RED(Color.fromString("#dc3545"));

    Color color;

    Colors(Color color) {
        this.color = color;
    }
}

