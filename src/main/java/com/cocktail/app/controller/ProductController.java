package com.cocktail.app.controller;

import com.cocktail.app.model.ProductWithCategory;
import com.cocktail.app.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;

    private ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    ResponseEntity<Iterable<ProductWithCategory>> getAll() {
        return ResponseEntity.ok(productRepository.findProductsWithCategoryName());
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductWithCategory> get(@PathVariable Long productId) {
        return ResponseEntity.ok(productRepository.findByProductIdWithCategoryName(productId));
    }
}
