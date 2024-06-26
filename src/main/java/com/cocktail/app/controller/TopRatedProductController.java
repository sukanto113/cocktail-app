package com.cocktail.app.controller;

import com.cocktail.app.model.ProductWithCategory;
import com.cocktail.app.model.TopRatedProduct;
import com.cocktail.app.repository.ProductRepository;
import com.cocktail.app.repository.RatingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("top-rated")
public class TopRatedProductController {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;

    private TopRatedProductController(RatingRepository ratingRepository, ProductRepository productRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    ResponseEntity<TopRatedProduct> get() {
        Optional<Long> productIdOptional = ratingRepository.getTopRatedProductId();
        if (productIdOptional.isPresent()) {
            Long productId = productIdOptional.get();
            ProductWithCategory product = productRepository.findByProductIdWithCategoryName(productId);
            Optional<Double> rating = ratingRepository.getAverageRating(productId);
            return ResponseEntity.ok(new TopRatedProduct(productId, product.categoryId(), product.categoryName(), product.name(), product.picture(), product.ingredients(), product.method(), rating.get()));
        }
        return ResponseEntity.notFound().build();

    }
}
