package com.openclassrooms.watchlist.validation;

import com.openclassrooms.watchlist.domain.Priority;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<ValidPriority, Priority> {
    @Override
    public boolean isValid(Priority value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        for (Priority priority : Priority.values()) {
            if (priority == value) {
                return true;
            }
        }
        return false;
    }
}
