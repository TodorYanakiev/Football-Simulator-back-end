package com.example.FootballManager_back_end.Utils;

import com.example.FootballManager_back_end.Exception.ApiRequestException;

public class ValidationUtils {

    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static void validateStringField(String value, String fieldName, int minLength) {
        if (value == null || value.length() < minLength)
            throw new ApiRequestException(fieldName + " must be at least " + minLength + " characters long.");
    }

    public static void validateNumber(int value, String fieldName, Integer minValue, Integer maxValue) {
        if ((minValue != null && value < minValue) || (maxValue != null && value > maxValue)) {
            String range = (minValue != null ? "at least " + minValue : "") +
                    (minValue != null && maxValue != null ? " and " : "") +
                    (maxValue != null ? "at most " + maxValue : "");
            throw new ApiRequestException(fieldName + " must be " + range + ".");
        }
    }
}
