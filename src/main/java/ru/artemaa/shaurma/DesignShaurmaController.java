package ru.artemaa.shaurma;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.artemaa.shaurma.data.IngredientRepository;
import ru.artemaa.shaurma.data.ShaurmaRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignShaurmaController {
    private final IngredientRepository ingredientRepository;
    private final ShaurmaRepository shaurmaRepository;

    @Autowired
    public DesignShaurmaController(IngredientRepository ingredientRepository, ShaurmaRepository shaurmaRepository) {
        this.ingredientRepository = ingredientRepository;
        this.shaurmaRepository = shaurmaRepository;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Shaurma shaurma() {
        return new Shaurma();
    }

    @GetMapping()
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        for (Ingredient.Type type : Ingredient.Type.values()) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        //model.addAttribute("design", new Shaurma());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Shaurma design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }

        Shaurma saved = shaurmaRepository.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(ingredient -> type == ingredient.getType()).collect(Collectors.toList());
    }
}
