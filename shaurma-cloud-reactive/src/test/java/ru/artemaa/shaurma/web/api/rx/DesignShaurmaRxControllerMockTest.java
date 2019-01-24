package ru.artemaa.shaurma.web.api.rx;

import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.artemaa.shaurma.Ingredient;
import ru.artemaa.shaurma.Shaurma;
import ru.artemaa.shaurma.data.jpa.rx.ShaurmaRepository;
import ru.artemaa.shaurma.web.DesignProperties;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.artemaa.shaurma.web.api.rx.DesignShaurmaRxController.BASE_URL;

public class DesignShaurmaRxControllerMockTest {

    @Test
    public void testRecent() {
        Shaurma[] shaurmas = {createTestShaurma(1L),
                createTestShaurma(2L),
                createTestShaurma(3L),
                createTestShaurma(4L),
                createTestShaurma(5L),
                createTestShaurma(6L),
                createTestShaurma(7L),
                createTestShaurma(8L),
                createTestShaurma(9L),
                createTestShaurma(10L),
                createTestShaurma(11L),
                createTestShaurma(12L),
                createTestShaurma(13L),
                createTestShaurma(14L),
                createTestShaurma(15L),
                createTestShaurma(16L)};

        Flux<Shaurma> shaurmaFlux = Flux.just(shaurmas);

        ShaurmaRepository shaurmaRepository = mock(ShaurmaRepository.class);
        when(shaurmaRepository.findAll()).thenReturn(shaurmaFlux);

        DesignProperties designProperties = new DesignProperties();
        designProperties.setRecentSize(12);

        WebTestClient testClient = WebTestClient.bindToController(new DesignShaurmaRxController(shaurmaRepository, designProperties))
                .build();

        testClient.get().uri(BASE_URL + "/recent")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Shaurma.class)
                    .contains(Arrays.copyOf(shaurmas, designProperties.getRecentSize()));

        //compare to json from file
        /*String json = StreamUtils.copyToString(DesignShaurmaRxControllerTest.class.getResourceAsStream(""), Charset.defaultCharset());
        testClient.get().uri(BASE_URL + "/recent")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(json);*/
    }

    //TODO implement when read ch 12
    @Test
    public void testGetById() {
    }

    @Test
    public void testPostShaurma() {
    }

    private Shaurma createTestShaurma(Long id) {
        return Shaurma.builder()
                .id(id)
                .name("Shaurma " + id)
                .ingredients(asList(
                        new Ingredient("ING1", "Ingredient 1", Ingredient.Type.WRAP),
                        new Ingredient("ING2", "Ingredient 2", Ingredient.Type.PROTEIN)
                ))
                .build();
    }
}