package com.cocktail.app.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public class CocktailOfTheDay {
    @Id
    private Long id;
    private LocalDate date;
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
