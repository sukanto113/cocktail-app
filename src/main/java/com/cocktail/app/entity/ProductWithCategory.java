package com.cocktail.app.entity;

public record ProductWithCategory(Long id, Long categoryId, String categoryName, String name, String picture,
                                  String ingredients, String method) {
}
