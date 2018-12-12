package ru.artemaa.shaurma.web.api.hateoas;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import ru.artemaa.shaurma.Ingredient;

@Getter
public class IngredientResource extends ResourceSupport {
    private String name;
    private Ingredient.Type type;

    public IngredientResource(Ingredient ingredient) {
        name = ingredient.getName();
        type = ingredient.getType();
    }
}
