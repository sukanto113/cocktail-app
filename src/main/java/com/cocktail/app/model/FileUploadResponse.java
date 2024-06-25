package com.cocktail.app.model;

public record FileUploadResponse(boolean success, String path, String error) {
}
