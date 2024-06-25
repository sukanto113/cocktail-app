package com.cocktail.app.model;

public record RegistrationResponse(boolean success, String token, String error) {
}
