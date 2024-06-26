package com.cocktail.app.repository;

import com.cocktail.app.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findByCategoryId(Long categoryId);
}
