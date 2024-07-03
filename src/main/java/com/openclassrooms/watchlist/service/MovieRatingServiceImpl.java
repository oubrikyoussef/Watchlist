package com.openclassrooms.watchlist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
@ConditionalOnProperty(prefix = "app", name = "environment", havingValue = "prod")
public class MovieRatingServiceImpl implements MovieRatingService {
    private static final Logger logger = LoggerFactory.getLogger(MovieRatingServiceImpl.class);

    @Value("${omdb.api.url}")
    private String apiUrl;

    @Override
    public Double getMovieRating(String title) {
        try {
            logger.info("OMDB Api called with URL: {}", apiUrl + title);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ObjectNode> response = restTemplate.getForEntity(apiUrl + "&t=" + title, ObjectNode.class);
            ObjectNode objectNode = response.getBody();
            Double rating = objectNode.path("imdbRating").asDouble();
            logger.debug("Rating for {} is {}", title, rating);
            return rating;
        } catch (Exception e) {
            logger.warn("Something went wrong calling OMDB API for {}", e.getMessage());
            return null;
        }
    }
}
