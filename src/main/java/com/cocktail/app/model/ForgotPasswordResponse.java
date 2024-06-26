package com.cocktail.app.model;

public record ForgotPasswordResponse(boolean success, String otpKey, String error) {
}
