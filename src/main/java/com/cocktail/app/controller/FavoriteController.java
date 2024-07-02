package com.cocktail.app.controller;

import com.cocktail.app.entity.Favorite;
import com.cocktail.app.model.FavoriteAddForm;
import com.cocktail.app.model.FavoriteAddResponse;
import com.cocktail.app.model.ProductWithCategory;
import com.cocktail.app.repository.FavoriteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {
    private final FavoriteRepository favoriteRepository;

    private FavoriteController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @GetMapping
    ResponseEntity<Iterable<ProductWithCategory>> get(@CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        return ResponseEntity.ok(favoriteRepository.findProductByUserId(userId));
    }

    @PostMapping
    ResponseEntity<FavoriteAddResponse> addFavorite(@RequestBody FavoriteAddForm formFields, @CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        Long productId = formFields.productId();
        Favorite existing = favoriteRepository.findByUserIdAndProductId(userId, productId);
        if (existing != null) {
            return ResponseEntity.ok(new FavoriteAddResponse(false, "Already favorite"));
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favoriteRepository.save(favorite);
        return ResponseEntity.ok(new FavoriteAddResponse(true, null));
    }

    @DeleteMapping
    ResponseEntity<FavoriteAddResponse> deleteFavorite(@RequestBody FavoriteAddForm formFields, @CurrentSecurityContext(expression = "authentication.principal") Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        Long productId = formFields.productId();
        Favorite existing = favoriteRepository.findByUserIdAndProductId(userId, productId);
        if (existing == null) {
            return ResponseEntity.ok(new FavoriteAddResponse(false, "Not favorite"));
        }
        favoriteRepository.delete(existing);
        return ResponseEntity.ok(new FavoriteAddResponse(true, null));
    }
}
