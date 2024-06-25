package com.cocktail.app;

import com.cocktail.app.models.RegistrationFormFields;
import com.cocktail.app.models.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/register")
    ResponseEntity<Map> register(@RequestBody RegistrationFormFields formFields) {
        UserInfo newUser = new UserInfo(null, formFields.fullName(), formFields.email(), formFields.password(), true, null, null, null, null, null);
        userRepository.save(newUser);
        HashMap map = new HashMap();
        map.put("success", true);
        return ResponseEntity.ok(map);
    }
}
