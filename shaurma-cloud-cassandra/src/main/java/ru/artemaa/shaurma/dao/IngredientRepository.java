package ru.artemaa.shaurma.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.artemaa.shaurma.model.Ingredient;

public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {
}
