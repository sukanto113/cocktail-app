package com.cocktail.app.controller;

import com.cocktail.app.model.RegistrationForm;
import com.cocktail.app.entity.UserInfo;
import com.cocktail.app.model.UserRegistrationResponse;
import com.cocktail.app.repository.UserRepository;
import com.cocktail.app.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final JwtService jwtService;


    private UserController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @GetMapping
    ResponseEntity<Optional<UserInfo>> getUser(@CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        return ResponseEntity.ok(userRepository.findById(userId));
    }

    @PostMapping("/register")
    ResponseEntity<UserRegistrationResponse> register(@RequestBody RegistrationForm formFields) {
        UserInfo newUser = new UserInfo(null, formFields.fullName(), formFields.email(), formFields.password(), true, null, null, null, null, null);
        UserInfo user = userRepository.save(newUser);
        String token = jwtService.createToken(user.id().toString());
        return ResponseEntity.ok(new UserRegistrationResponse(true, token, null));
    }
}
