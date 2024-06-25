package com.cocktail.app.model;

public record LoginResponse(boolean success, String token, String error) {
}
