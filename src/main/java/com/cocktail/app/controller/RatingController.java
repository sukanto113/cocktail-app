package com.cocktail.app.controller;

import com.cocktail.app.entity.Rating;
import com.cocktail.app.model.*;
import com.cocktail.app.repository.RatingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingRepository ratingRepository;

    private RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @GetMapping
    ResponseEntity<ProductRatingResponse> get(@RequestBody RatingGetForm formFields, @CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        Long productId = formFields.productId();
        Rating existing = ratingRepository.findByUserIdAndProductId(userId, productId);
        Optional<Double> averageRatingOptional = ratingRepository.getAverageRating(productId);
        Double averageRating = null;
        if (averageRatingOptional.isPresent()) {
            averageRating = averageRatingOptional.get();
        }
        System.out.println();
        if (existing != null) {
            return ResponseEntity.ok(new ProductRatingResponse(averageRating, existing.getRating(), existing.getReview()));
        } else {
            return ResponseEntity.ok(new ProductRatingResponse(averageRating, null, null));
        }
    }

    @PostMapping
    ResponseEntity<RatingAddResponse> add(@RequestBody RatingAddForm formFields, @CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        Long productId = formFields.productId();
        Rating existing = ratingRepository.findByUserIdAndProductId(userId, productId);
        if (existing != null) {
            return ResponseEntity.ok(new RatingAddResponse(false, "Already rated"));
        }
        Rating rating = new Rating();
        rating.setUserId(userId);
        rating.setProductId(productId);
        rating.setRating(formFields.rating());
        rating.setReview(formFields.review());
        ratingRepository.save(rating);
        return ResponseEntity.ok(new RatingAddResponse(true, null));
    }
}
