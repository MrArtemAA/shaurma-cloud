package ru.artemaa.shaurma.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.artemaa.shaurma.Ingredient;
import ru.artemaa.shaurma.data.jpa.IngredientRepository;

@Component
public class IngredientToIdConverter implements Converter<String, Ingredient> {
    private final IngredientRepository ingredientRepository;

    public IngredientToIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository
                .findById(id)
                .orElse(null);
    }
}
