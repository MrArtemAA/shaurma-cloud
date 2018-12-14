package ru.artemaa.shaurma.web.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.junit4.SpringRunner;
import ru.artemaa.shaurma.Ingredient;

import java.net.URI;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraversonTest {
    /*@Autowired
    private Traverson traverson;*/

    @Test
    public void testGetIngredients() {
        ParameterizedTypeReference<Resources<Ingredient>> typeReference = new ParameterizedTypeReference<Resources<Ingredient>>() {
        };

        Traverson traverson = new Traverson(URI.create("http://localhost:9090/api"), MediaTypes.HAL_JSON);
        Resources<Ingredient> ingredientResources = traverson.follow("ingredientResources").toObject(typeReference);
        Collection<Ingredient> ingredients = ingredientResources.getContent();
        assertEquals(10, ingredients.size());
    }
}
