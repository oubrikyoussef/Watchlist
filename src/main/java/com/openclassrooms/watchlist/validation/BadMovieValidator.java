package com.openclassrooms.watchlist.validation;

import com.openclassrooms.watchlist.domain.WatchlistItem;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BadMovieValidator implements ConstraintValidator<BadMovie, WatchlistItem> {
    private final int MIN_LENGTH = 15;

    @Override
    public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {
        return !((value.getComment().length() <= MIN_LENGTH) && value.getRating() <= 6);
    }
}
