package com.openclassrooms.watchlist.service;

import java.util.Random;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "app", name = "environment", havingValue = "dev")
public class MovieRatingServiceMockImpl implements MovieRatingService {
    @Override
    public Double getMovieRating(String title) {
        Random random = new Random();
        double rating = random.nextDouble() * 10;
        return Math.round(rating * 10.0) / 10.0;
    }
}