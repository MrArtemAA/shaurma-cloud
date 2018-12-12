package ru.artemaa.shaurma.web.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.artemaa.shaurma.Ingredient;
import ru.artemaa.shaurma.Shaurma;
import ru.artemaa.shaurma.data.jpa.IngredientRepository;
import ru.artemaa.shaurma.data.jpa.ShaurmaRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DesignShaurmaRestController.class)
@Transactional
//TODO: implement rest tests
public class DesignShaurmaRestControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ShaurmaRepository shaurmaRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    private List<Shaurma> shaurmas;

    @PostConstruct
    public void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Before
    public void setUpTest() {
        shaurmas = new ArrayList<>();
        shaurmas.add(shaurmaRepository.save(createShaurma("Shaurma 1", asList(
                ingredientRepository.findById("RGPT").get(),
                ingredientRepository.findById("CHKN").get()
        ))));

        shaurmas.add(shaurmaRepository.save(createShaurma("Shaurma 2", asList(
                ingredientRepository.findById("CHPT").get(),
                ingredientRepository.findById("PORK").get(),
                ingredientRepository.findById("CHED").get()
        ))));
    }

    @Test
    public void testGetRecent() {
    }

    @Test
    public void testGetById() throws Exception {
        Shaurma shaurma = shaurmas.iterator().next();
        mockMvc.perform(get("/design/" + shaurma.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
                //.andExpect(content().string(OBJECT_MAPPER.writeValueAsString(shaurma)));
    }

    @Test
    public void testPostShaurma() {
    }

    private Shaurma createShaurma(String name, List<Ingredient> ingredients) {
        Shaurma shaurma = new Shaurma();
        shaurma.setName(name);
        shaurma.setIngredients(ingredients);
        return shaurma;
    }
}