package com.cocktail.app.model;

public record UserRegistrationResponse(boolean success, String token, String error) {
}
