package com.cocktail.app.repository;

import com.cocktail.app.entity.Rating;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Rating findByUserIdAndProductId(Long userId, Long productId);
    @Query("SELECT AVG(rating) AS average_rating FROM rating WHERE product_id=:productId GROUP BY product_id;")
    Optional<Double> getAverageRating(@Param("productId") Long productId);

}
