package com.cocktail.app.model;

public record ProductWithMyReview(Long id, Long categoryId, String categoryName, String name, String picture,
                                  String ingredients, String method, int rating, String review) {
}
