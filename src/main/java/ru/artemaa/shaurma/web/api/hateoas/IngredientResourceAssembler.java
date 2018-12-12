package ru.artemaa.shaurma.web.api.hateoas;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import ru.artemaa.shaurma.Ingredient;
import ru.artemaa.shaurma.web.api.IngredientRestController;

public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {
    public IngredientResourceAssembler() {
        super(IngredientRestController.class, IngredientResource.class);
    }

    @Override
    public IngredientResource toResource(Ingredient ingredient) {
        return createResourceWithId(ingredient.getId(), ingredient);
    }

    @Override
    protected IngredientResource instantiateResource(Ingredient ingredient) {
        return new IngredientResource(ingredient);
    }
}
