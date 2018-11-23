package ru.artemaa.shaurma;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static ru.artemaa.shaurma.Ingredient.Type.*;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignShaurmaController {
    @GetMapping()
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = asList(
                new Ingredient("RGPT", "Regular Pita", WRAP),
                new Ingredient("CHPT", "Cheesy Pita", WRAP),
                new Ingredient("CHKN", "Chicken", PROTEIN),
                new Ingredient("PORK", "Pork", PROTEIN),
                new Ingredient("TMT", "Tomatoes", VEGGIES),
                new Ingredient("CBG", "Cabbage", VEGGIES),
                new Ingredient("CHED", "Cheddar", CHEESE),
                new Ingredient("JACK", "Monterry Jack", CHEESE),
                new Ingredient("SLSA", "Salsa", SAUCE),
                new Ingredient("SRCR", "Sour Cream", SAUCE)
        );

        for (Ingredient.Type type : Ingredient.Type.values()) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        model.addAttribute("design", new Shaurma());

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Shaurma design, Errors errors) {
        if (errors.hasErrors()) {
            return "design";
        }

        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients.stream().filter(ingredient -> type == ingredient.getType()).collect(Collectors.toList());
    }
}
