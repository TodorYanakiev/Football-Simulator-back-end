package com.example.FootballManager_back_end.Utils;

import com.example.FootballManager_back_end.Exception.ApiRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTests {
    @Test
    void testValidateStringFieldWhenValidInput() {
        ValidationUtils.validateStringField("John", "First name", 2);
    }

    @Test
    void testValidateStringFieldWhenNullValue() {
        ApiRequestException exception = assertThrows(ApiRequestException.class, () ->
                ValidationUtils.validateStringField(null, "First name", 2));
        assertEquals("First name must be at least 2 characters long.", exception.getMessage());
    }

    @Test
    void testValidateStringFieldWhenShortValue() {
        ApiRequestException exception = assertThrows(ApiRequestException.class, () ->
                ValidationUtils.validateStringField("A", "First name", 2));
        assertEquals("First name must be at least 2 characters long.", exception.getMessage());
    }

    @Test
    void testValidateNumberWhenValidInput() {
        ValidationUtils.validateNumber(25, "Age", 15, 99);
    }

    @Test
    void testValidateNumberWhenNoMinOrMax() {
        ValidationUtils.validateNumber(50, "Custom field", null, null);
    }

    @Test
    void testValidateNumberWhenBelowMinValue() {
        int value = 5;
        String fieldName = "Age";
        Integer minValue = 10;
        Integer maxValue = null;

        ApiRequestException exception = assertThrows(ApiRequestException.class, () ->
                ValidationUtils.validateNumber(value, fieldName, minValue, maxValue)
        );
        assertEquals("Age must be at least 10.", exception.getMessage());
    }

    @Test
    void testValidateNumberWhenAboveMaxValue() {
        int value = 150;
        String fieldName = "Shirt Number";
        Integer minValue = null;
        Integer maxValue = 99;

        ApiRequestException exception = assertThrows(ApiRequestException.class, () ->
                ValidationUtils.validateNumber(value, fieldName, minValue, maxValue)
        );
        assertEquals("Shirt Number must be at most 99.", exception.getMessage());
    }

    @Test
    void testValidateNumberWhenBelowMinAndAboveMaxValue() {
        int value = 150;
        String fieldName = "Speed";
        Integer minValue = 1;
        Integer maxValue = 99;

        ApiRequestException exception = assertThrows(ApiRequestException.class, () ->
                ValidationUtils.validateNumber(value, fieldName, minValue, maxValue)
        );
        assertEquals("Speed must be at least 1 and at most 99.", exception.getMessage());
    }

    @Test
    void testValidateNumberWhenValidValue() {
        int value = 50;
        String fieldName = "Dribble";
        Integer minValue = 1;
        Integer maxValue = 99;

        assertDoesNotThrow(() ->
                ValidationUtils.validateNumber(value, fieldName, minValue, maxValue)
        );
    }
}
