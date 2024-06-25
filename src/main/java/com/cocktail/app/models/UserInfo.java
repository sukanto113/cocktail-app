package com.cocktail.app.models;

import org.springframework.data.annotation.Id;

public record UserInfo(@Id Long id, String fullName, String email, String password, boolean active, String picture,
                       String phone, String street, String city, String country) {
}
