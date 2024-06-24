package com.cocktail.app;

import org.springframework.data.annotation.Id;

public record UserInfo(@Id Long id, String fullName, String email, String password) {
}
