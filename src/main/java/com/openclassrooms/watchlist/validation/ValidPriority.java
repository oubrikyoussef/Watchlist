package com.openclassrooms.watchlist.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PriorityValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPriority {
    String message() default "Please enter MEDIUM, LOW, or HIGH for priority (uppercase)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}