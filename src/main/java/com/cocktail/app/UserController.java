package com.cocktail.app;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    private UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    ResponseEntity<Iterable<UserInfo>> getUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}
