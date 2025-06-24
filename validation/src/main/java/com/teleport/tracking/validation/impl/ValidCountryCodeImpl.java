package com.teleport.tracking.validation.impl;

import com.teleport.tracking.validation.ValidCountryCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;

import static java.util.Locale.IsoCountryCode.PART1_ALPHA2;

public class ValidCountryCodeImpl implements ConstraintValidator<ValidCountryCode, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() != 2) return false;
        return Locale.getISOCountries(PART1_ALPHA2).contains(value);
    }
}