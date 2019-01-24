package ru.artemaa.shaurma.data.jdbc;

import ru.artemaa.shaurma.entity.jpa.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
