package com.cocktail.app.repository;

import com.cocktail.app.entity.Product;
import com.cocktail.app.model.ProductWithCategory;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p.id, p.category_id, c.name as category_name, p.name, p.picture, p.ingredients, p.method  FROM product AS p LEFT JOIN category as c on p.category_id=c.id WHERE p.category_id=:categoryId")
    Iterable<ProductWithCategory> findByCategoryIdWithCategoryName(@Param("categoryId") Long categoryId);
}
