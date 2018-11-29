package ru.artemaa.shaurma.data.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.artemaa.shaurma.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
