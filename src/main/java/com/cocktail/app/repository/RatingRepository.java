package com.cocktail.app.repository;

import com.cocktail.app.entity.Rating;
import com.cocktail.app.model.ProductWithCategory;
import com.cocktail.app.model.ProductWithMyReview;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Rating findByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT AVG(rating) AS average_rating FROM rating WHERE product_id=:productId GROUP BY product_id;")
    Optional<Double> getAverageRating(@Param("productId") Long productId);

    @Query("SELECT product_id FROM rating GROUP BY product_id ORDER BY AVG(rating) DESC LIMIT 1;")
    Optional<Long> getTopRatedProductId();

    @Query("SELECT p.id, p.category_id, c.name as category_name, p.name, p.picture, p.ingredients, p.method, r.rating, r.review FROM rating as r LEFT JOIN product as p on r.product_id=p.id LEFT JOIN category as c on p.category_id=c.id WHERE r.user_id=:userId;")
    Iterable<ProductWithMyReview> findByUserId(@Param("userId") Long userId);

}
