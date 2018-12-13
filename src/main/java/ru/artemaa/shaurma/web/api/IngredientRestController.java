package ru.artemaa.shaurma.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemaa.shaurma.Ingredient;
import ru.artemaa.shaurma.data.jpa.IngredientRepository;

@RestController
@RequestMapping(value = "/rest/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientRestController {
    private final IngredientRepository ingredientRepository;

    public IngredientRestController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public Iterable<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getById(@PathVariable("id") String id) {
        return ingredientRepository.findById(id)
                .map(ingredient -> new ResponseEntity<>(ingredient, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Ingredient put(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }
}
