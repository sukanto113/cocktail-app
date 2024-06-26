package com.cocktail.app.repository;

import com.cocktail.app.entity.Favorite;
import com.cocktail.app.model.ProductWithCategory;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
    Favorite findByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT p.id, p.category_id, c.name as category_name, p.name, p.picture, p.ingredients, p.method FROM favorite as f LEFT JOIN product as p on f.product_id=p.id LEFT JOIN category as c on p.category_id=c.id WHERE f.user_id=:userId;")
    Iterable<ProductWithCategory> findProductByUserId(@Param("userId") Long userId);
}
