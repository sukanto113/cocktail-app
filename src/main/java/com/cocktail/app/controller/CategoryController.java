package com.cocktail.app.controller;

import com.cocktail.app.entity.Category;
import com.cocktail.app.entity.Product;
import com.cocktail.app.entity.ProductWithCategory;
import com.cocktail.app.repository.CategoryRepository;
import com.cocktail.app.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    private CategoryController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    ResponseEntity<Iterable<Category>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Iterable<ProductWithCategory>> productByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productRepository.findByCategoryIdWithCategoryName(categoryId));
    }
}
