package com.openclassrooms.watchlist.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<ValidRating, Double> {
    final int MIN_RATING = 1, MAX_RATING = 10;

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value >= MIN_RATING && value <= MAX_RATING;
    }

}
