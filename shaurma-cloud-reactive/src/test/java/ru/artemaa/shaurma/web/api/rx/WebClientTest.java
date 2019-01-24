package ru.artemaa.shaurma.web.api.rx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.artemaa.shaurma.Ingredient;
import ru.artemaa.shaurma.data.jpa.IngredientRepository;

import java.time.Duration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class WebClientTest {
    private static final String INGREDIENT_ID = "RGPT";

    @Autowired
    private WebClient webClient;
    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void testGetIngredientById() {
        webClient
                .get()
                .uri("/ingredients/{id}", INGREDIENT_ID)
                .retrieve()
                .onStatus(httpStatus -> httpStatus == HttpStatus.NOT_FOUND,
                        clientResponse -> Mono.just(new RuntimeException("Ingredient not found by id: " + INGREDIENT_ID)))
                .bodyToMono(Ingredient.class)
                .subscribe(this::assertIngredient);
    }

    @Test
    public void testGetIngredientById_exchange() {
        webClient
                .get()
                .uri("/ingredients/{id}", INGREDIENT_ID)
                .exchange()
                .flatMap(clientResponse -> {
                    if (clientResponse.headers().header("X_UNAVAILABLE").contains("true")) {
                        return Mono.empty();
                    }
                    return Mono.just(clientResponse);
                })
                .flatMap(clientResponse -> clientResponse.bodyToMono(Ingredient.class))
                .subscribe(this::assertIngredient);
    }

    @Test
    public void testGetIngredients() {
        webClient
                .get()
                .uri("/ingredients")
                .retrieve()
                .bodyToFlux(Ingredient.class)
                .timeout(Duration.ofSeconds(1))
                .subscribe(
                        System.out::println,
                        System.err::println
                );
    }

    @Test
    public void testPostIngredient() {
        Ingredient ingredient = new Ingredient("TST1", "Test 1", Ingredient.Type.WRAP);
        webClient
                .post()
                .uri("/ingredients")
                .body(Mono.just(ingredient), Ingredient.class)
                .retrieve()
                .bodyToMono(Ingredient.class)
                .subscribe(savedIngredient -> assertEquals(ingredient, savedIngredient));
    }

    @Test
    public void testPutIngredient() {
        Ingredient ingredient = ingredientRepository.findById(INGREDIENT_ID).get();
        Ingredient updatedIngredient = new Ingredient(ingredient.getId(), "Updated ingredient", ingredient.getType());
        webClient
                .put()
                .uri("/ingredients/{id}", updatedIngredient.getId())
                .syncBody(updatedIngredient) //for raw types
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(aVoid -> assertEquals(
                        updatedIngredient,
                        ingredientRepository.findById(updatedIngredient.getId())
                ));
    }

    @Test
    public void testDeleteIngredient() {
        webClient
                .delete()
                .uri("/ingredients/{id}", "CHPT")
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe(aVoid -> assertFalse(ingredientRepository.existsById("CHPT")));
    }

    private void assertIngredient(Ingredient ingredient) {
        assertNotNull(ingredient);
        assertEquals("Regular Pita", ingredient.getName());
        assertEquals(Ingredient.Type.WRAP, ingredient.getType());
    }
}
