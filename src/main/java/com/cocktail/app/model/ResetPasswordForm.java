package com.cocktail.app.model;

public record ResetPasswordForm(String email, String otp, String otpKey, String newPassword) {
}
