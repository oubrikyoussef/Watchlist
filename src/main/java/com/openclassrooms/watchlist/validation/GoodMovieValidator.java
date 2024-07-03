package com.openclassrooms.watchlist.validation;

import com.openclassrooms.watchlist.domain.WatchlistItem;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GoodMovieValidator implements ConstraintValidator<GoodMovie, WatchlistItem> {

    @Override
    public boolean isValid(WatchlistItem value, ConstraintValidatorContext context) {
        return !(value.getRating() >= 8 && "LOW".equals(value.getPriority().name()));
    }
}
