package ru.artemaa.shaurma.web.api.rx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.artemaa.shaurma.Shaurma;
import ru.artemaa.shaurma.data.jpa.ShaurmaRepository;

import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DesignShaurmaRxControllerTest {

    @Autowired
    private WebTestClient testClient;
    @Autowired
    private ShaurmaRepository shaurmaRepository;

    //TODO check when implement reactive repositories in Ch.12
    @Test
    public void testRecent() {
        testClient.get().uri(DesignShaurmaRxController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Shaurma.class)
                    .contains(StreamSupport
                            .stream(shaurmaRepository.findAll().spliterator(), false)
                            .toArray(Shaurma[]::new)
                    );
    }
}