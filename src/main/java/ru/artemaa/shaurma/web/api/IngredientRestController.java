package ru.artemaa.shaurma.web.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/design", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientRestController {

}
