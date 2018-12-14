package ru.artemaa.shaurma.web.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.artemaa.shaurma.Ingredient;
import ru.artemaa.shaurma.data.jpa.IngredientRepository;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {
    private static final String URL_INGREDIENT_BY_ID = "http://localhost:9090/rest/ingredients/{id}";
    private static final String URL_INGREDIENTS = "http://localhost:9090/rest/ingredients";
    private static final String INGREDIENT_ID = "RGPT";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void testGetIngredientById_forObjectVariableList() {
        Ingredient ingredient = restTemplate.getForObject(
                URL_INGREDIENT_BY_ID,
                Ingredient.class,
                INGREDIENT_ID
        );
        assertIngredient(ingredient);
    }

    @Test
    public void testGetIngredientById_forObjectVariableMap() {
        Map<String, String> uriVariables = new HashMap() {{ put("id", INGREDIENT_ID); }};
        Ingredient ingredient = restTemplate.getForObject(
                URL_INGREDIENT_BY_ID,
                Ingredient.class,
                uriVariables
        );
        assertIngredient(ingredient);
    }

    @Test
    public void testGetIngredientById_forObjectUriVariableMap() {
        Map<String, String> uriVariables = new HashMap() {{ put("id", INGREDIENT_ID); }};
        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL_INGREDIENT_BY_ID)
                .build(uriVariables);

        Ingredient ingredient = restTemplate.getForObject(uri, Ingredient.class);
        assertIngredient(ingredient);
    }

    @Test
    public void testGetIngredientById_forEntity() {
        ResponseEntity<Ingredient> responseEntity = restTemplate.getForEntity(
                URL_INGREDIENT_BY_ID,
                Ingredient.class,
                INGREDIENT_ID
        );

        assertIngredient(responseEntity.getBody());
    }

    @Test
    public void testPutIngredient() {
        //TODO fix forbidden
        Ingredient ingredient = ingredientRepository.findById(INGREDIENT_ID).get();
        Ingredient updatedIngredient = new Ingredient(ingredient.getId(), "Updated ingredient", ingredient.getType());
        restTemplate.put(
                URL_INGREDIENT_BY_ID,
                updatedIngredient,
                updatedIngredient.getId()
        );
        Ingredient savedIngredient = ingredientRepository.findById(updatedIngredient.getId()).get();
        assertEquals(updatedIngredient, savedIngredient);
    }

    @Test
    public void testDeleteIngredient() {
        //TODO: check, fix forbidden if necessary
        restTemplate.delete(URL_INGREDIENT_BY_ID, "CHPT");
        assertFalse(ingredientRepository.existsById("CHPT"));
    }

    @Test
    //TODO: check, fix forbidden if necessary
    public void testPostIngredient_forObject() {
        Ingredient ingredient = new Ingredient("ONON", "Sweet onion", Ingredient.Type.VEGGIES);
        Ingredient savedIngredient = restTemplate.postForObject(
                URL_INGREDIENTS,
                ingredient,
                Ingredient.class
        );
        assertEquals(ingredient, savedIngredient);
    }

    @Test
    //TODO: check, fix forbidden if necessary
    public void testPostIngredient_forLocation() {
        Ingredient ingredient = new Ingredient("ONON2", "Sweet onion 2", Ingredient.Type.VEGGIES);
        URI uri = restTemplate.postForLocation(URL_INGREDIENTS, ingredient);
        assertEquals(URL_INGREDIENTS + "/" + ingredient.getId(), uri.toString());
    }

    @Test
    //TODO: check, fix forbidden if necessary
    public void testPostIngredient_forEntity() {
        Ingredient ingredient = new Ingredient("ONON3", "Sweet onion 3", Ingredient.Type.VEGGIES);
        ResponseEntity<Ingredient> responseEntity = restTemplate.postForEntity(
                URL_INGREDIENTS,
                ingredient,
                Ingredient.class
        );
        assertEquals(ingredient, responseEntity.getBody());
    }

    private void assertIngredient(Ingredient ingredient) {
        assertNotNull(ingredient);
        assertEquals("Regular Pita", ingredient.getName());
        assertEquals(Ingredient.Type.WRAP, ingredient.getType());
    }
}
