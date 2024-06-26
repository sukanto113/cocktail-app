package com.cocktail.app.controller;

import com.cocktail.app.entity.Category;
import com.cocktail.app.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    private CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    ResponseEntity<Iterable<Category>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}
