package com.cocktail.app.controller;

import com.cocktail.app.entity.CocktailOfTheDay;
import com.cocktail.app.model.CocktailOfTheDayResponse;
import com.cocktail.app.model.ProductWithCategory;
import com.cocktail.app.repository.CocktailOfTheDayRepository;
import com.cocktail.app.repository.ProductRepository;
import com.cocktail.app.repository.RatingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/cocktail-of-the-day")
public class CocktailOfTheDayController {

    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final CocktailOfTheDayRepository cocktailOfTheDayRepository;

    private CocktailOfTheDayController(RatingRepository ratingRepository, ProductRepository productRepository, CocktailOfTheDayRepository cocktailOfTheDayRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.cocktailOfTheDayRepository = cocktailOfTheDayRepository;
    }

    @GetMapping("/{date}")
    ResponseEntity<CocktailOfTheDayResponse> get(@PathVariable LocalDate date) {
        Optional<CocktailOfTheDay> existingCocktail = cocktailOfTheDayRepository.findByDate(date);
        Long productId;
        if (existingCocktail.isPresent()) {
            productId = existingCocktail.get().getProductId();
        } else {
            productId = productRepository.getRandomProductId();
            CocktailOfTheDay cocktail = new CocktailOfTheDay();
            cocktail.setDate(date);
            cocktail.setProductId(productId);
            cocktailOfTheDayRepository.save(cocktail);
        }

        ProductWithCategory product = productRepository.findByProductIdWithCategoryName(productId);
        Optional<Double> ratingOptional = ratingRepository.getAverageRating(productId);
        Double rating = null;
        if (ratingOptional.isPresent()) {
            rating = ratingOptional.get();
        }
        return ResponseEntity.ok(new CocktailOfTheDayResponse(productId, product.categoryId(), product.categoryName(), product.name(), product.picture(), product.ingredients(), product.method(), rating));
    }
}
