package com.cocktail.app.model;

import jakarta.annotation.Nullable;

public record ProductWithCategoryAndRating(Long id, Long categoryId, String categoryName, String name, String picture,
                                           String ingredients, String method, Double averageRating) {
}
