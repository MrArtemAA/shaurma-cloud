package ru.artemaa.shaurma.web.api.hateoas;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;
import ru.artemaa.shaurma.entity.jpa.Shaurma;

import java.util.Date;
import java.util.List;

@Getter
@Relation(value = "shaurma", collectionRelation = "shaurmas")
public class ShaurmaResource extends ResourceSupport {
    private static final IngredientResourceAssembler ASSEMBLER = new IngredientResourceAssembler();

    private final String name;
    private final Date createdAt;
    private final List<IngredientResource> ingredients;

    public ShaurmaResource(Shaurma shaurma) {
        name = shaurma.getName();
        createdAt = shaurma.getCreatedAt();
        ingredients = ASSEMBLER.toResources(shaurma.getIngredients());
    }
}
