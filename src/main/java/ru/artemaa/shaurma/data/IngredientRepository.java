package ru.artemaa.shaurma.data;

import ru.artemaa.shaurma.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
