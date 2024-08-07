package com.cocktail.app.repository;

import com.cocktail.app.entity.Product;
import com.cocktail.app.model.ProductWithCategory;
import com.cocktail.app.model.ProductWithCategoryAndRating;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p.id, p.category_id, c.name as category_name, p.name, p.picture, p.ingredients, p.method  FROM product AS p LEFT JOIN category as c on p.category_id=c.id WHERE p.category_id=:categoryId")
    Iterable<ProductWithCategory> findByCategoryIdWithCategoryName(@Param("categoryId") Long categoryId);

    @Query("SELECT p.id, p.category_id, c.name as category_name, p.name, p.picture, p.ingredients, p.method, AVG(r.rating) AS average_rating FROM product AS p LEFT JOIN category as c on p.category_id=c.id LEFT JOIN rating as r on p.id=r.product_id GROUP BY p.id;")
    Iterable<ProductWithCategoryAndRating> findProductsWithCategoryNameAndRating();

    @Query("SELECT p.id, p.category_id, c.name as category_name, p.name, p.picture, p.ingredients, p.method  FROM product AS p LEFT JOIN category as c on p.category_id=c.id WHERE p.id=:productId")
    ProductWithCategory findByProductIdWithCategoryName(@Param("productId") Long productId);

    @Query("SELECT id FROM product ORDER BY RAND() LIMIT 1;")
    Long getRandomProductId();
}
