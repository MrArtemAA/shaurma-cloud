package ru.artemaa.shaurma.web.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import ru.artemaa.shaurma.Ingredient;
import ru.artemaa.shaurma.Shaurma;
import ru.artemaa.shaurma.Ingredient;

import java.net.URI;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraversonTest {
    @Autowired
    private RestTemplate restTemplate;

    private Traverson traverson = new Traverson(
            URI.create("http://localhost:9090/api"),
            MediaTypes.HAL_JSON
    );

    @Test
    public void testGetIngredients() {
        ParameterizedTypeReference<Resources<Ingredient>> typeReference =
                new ParameterizedTypeReference<Resources<Ingredient>>() {};

        Resources<Ingredient> ingredientResources = traverson
                .follow("ingredients")
                .toObject(typeReference);
        Collection<Ingredient> ingredients = ingredientResources.getContent();
        assertEquals(10, ingredients.size());
    }

    @Test
    public void testGetShaurmasRecent() {
        ParameterizedTypeReference<Resources<Shaurma>> typeReference =
                new ParameterizedTypeReference<Resources<Shaurma>>() {};

        Resources<Shaurma> shaurmaResources = traverson
                .follow("shaurmas")
                .follow("recents")
                //.follow("shaurmas", "recents")
                .toObject(typeReference);
        Collection<Shaurma> shaurmas = shaurmaResources.getContent();
        assertEquals(2, shaurmas.size());
    }

    @Test
    //TODO fix 403 forbidden
    public void testPostIngredient() {
        String ingredientsUrl = traverson
                .follow("ingredients")
                .asLink()
                .getHref();

        Ingredient ingredient = new Ingredient("ONT", "Sweet onion T", Ingredient.Type.VEGGIES);
        Ingredient savedIngredient = restTemplate.postForObject(
                ingredientsUrl,
                ingredient,
                Ingredient.class
        );
        assertEquals(ingredient, savedIngredient);
    }
}
