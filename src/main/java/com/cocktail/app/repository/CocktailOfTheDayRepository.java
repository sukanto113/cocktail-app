package com.cocktail.app.repository;

import com.cocktail.app.entity.CocktailOfTheDay;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CocktailOfTheDayRepository extends CrudRepository<CocktailOfTheDay, Long> {
    Optional<CocktailOfTheDay> findByDate(LocalDate date);
}
