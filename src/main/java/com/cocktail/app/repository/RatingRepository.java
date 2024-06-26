package com.cocktail.app.repository;

import com.cocktail.app.entity.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Rating findByUserIdAndProductId(Long userId, Long productId);
}
