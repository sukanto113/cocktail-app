package com.cocktail.app.model;

public record TopRatedProduct(Long id, Long categoryId, String categoryName, String name, String picture,
                              String ingredients, String method, Double rating) {
}
