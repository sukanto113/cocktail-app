package com.cocktail.app.controller;

import com.cocktail.app.model.*;
import com.cocktail.app.entity.UserInfo;
import com.cocktail.app.repository.UserRepository;
import com.cocktail.app.security.JwtService;
import org.apache.juli.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);


    private UserController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @GetMapping
    ResponseEntity<ProfileInfo> getUser(@CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        Optional<UserInfo> userInfo = userRepository.findById(userId);
        if (userInfo.isPresent()) {
            UserInfo user = userInfo.get();
            return ResponseEntity.ok(new ProfileInfo(user.getFullName(), user.getEmail(), user.getPhone(), user.getPicture(), user.getStreet(), user.getCity(), user.getCountry()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<ProfileInfo> getUser(@RequestBody ProfileInfo formFields, @CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        Optional<UserInfo> userInfoOptional = userRepository.findById(userId);
        if (userInfoOptional.isPresent()) {
            UserInfo user = userInfoOptional.get();
            if (formFields.fullName() != null){
                user.setFullName(formFields.fullName());
            }
            if(formFields.phone() != null) {
                user.setPhone(formFields.phone());
            }
            if(formFields.street() != null) {
                user.setStreet(formFields.street());
            }
            if(formFields.city() != null){
                user.setCity(formFields.city());
            }
            if(formFields.country() != null) {
                user.setCountry(formFields.country());
            }
            userRepository.save(user);
            return ResponseEntity.ok(new ProfileInfo(user.getFullName(), user.getEmail(), user.getPhone(), user.getPicture(), user.getStreet(), user.getCity(), user.getCountry()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationForm formFields) {
        UserInfo newUser = new UserInfo();
        newUser.setFullName(formFields.fullName());
        newUser.setEmail(formFields.email());
        newUser.setPassword(encoder.encode(formFields.password()));
        newUser.setActive(true);

        UserInfo user = userRepository.save(newUser);
        String token = jwtService.createToken(user.getId().toString());
        return ResponseEntity.ok(new RegistrationResponse(true, token, null));
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginForm formFields) {
        UserInfo user = userRepository.findByEmail(formFields.email());
        if (user != null) {
            if (encoder.matches(formFields.password(), user.getPassword())) {
                String token = jwtService.createToken(user.getId().toString());
                return ResponseEntity.ok(new LoginResponse(true, token, null));
            }
        }
        return ResponseEntity.ok(new LoginResponse(false, null, "Invalid email or password"));
    }

    @PostMapping("/change-password")
    ResponseEntity<ChangePasswordResponse> register(@RequestBody ChangePasswordForm formFields, @CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());

        Optional<UserInfo> userInfoOptional = userRepository.findById(userId);
        if (userInfoOptional.isPresent()) {
            UserInfo user = userInfoOptional.get();
            if (encoder.matches(formFields.oldPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(formFields.newPassword()));
                userRepository.save(user);
                return ResponseEntity.ok(new ChangePasswordResponse(true, null));
            } else {
                return ResponseEntity.ok(new ChangePasswordResponse(false, "Wrong old password"));
            }
        } else {
            return ResponseEntity.ok(new ChangePasswordResponse(false, "User not found"));
        }
    }
}
