package com.example.FootballManager_back_end.Utils;

import com.example.FootballManager_back_end.Exception.ApiRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationUtilsTests {
    @Test
    void testValidateStringFieldWhenValidInput() {
        ValidationUtils.validateStringField("John", "First name", 2);
    }

    @Test
    void testValidateStringFieldWhenNullValue() {
        ApiRequestException exception = Assertions.assertThrows(ApiRequestException.class, () ->
                ValidationUtils.validateStringField(null, "First name", 2));
        Assertions.assertEquals("First name must be at least 2 characters long.", exception.getMessage());
    }

    @Test
    void testValidateStringFieldWhenShortValue() {
        ApiRequestException exception = Assertions.assertThrows(ApiRequestException.class, () ->
                ValidationUtils.validateStringField("A", "First name", 2));
        Assertions.assertEquals("First name must be at least 2 characters long.", exception.getMessage());
    }

    @Test
    void testValidateNumberWhenValidInput() {
        ValidationUtils.validateNumber(25, "Age", 15, 99);
    }

    @Test
    void testValidateNumberWhenNoMinOrMax() {
        ValidationUtils.validateNumber(50, "Custom field", null, null);
    }
}
