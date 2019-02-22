package ru.artemaa.shaurma.web.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.artemaa.shaurma.dao.ShaurmaRepository;
import ru.artemaa.shaurma.model.Shaurma;
import ru.artemaa.shaurma.web.DesignProperties;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    private final ShaurmaRepository shaurmaRepository;
    private final DesignProperties designProperties;

    public RouterFunctionConfig(ShaurmaRepository shaurmaRepository, DesignProperties designProperties) {
        this.shaurmaRepository = shaurmaRepository;
        this.designProperties = designProperties;
    }

    //TODO fix for location
    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/rest/rx/router/design/recent"), this::recent)
                .andRoute(POST("/rest/rx/router/design"), this::postShaurma);
    }

    private Mono<ServerResponse> recent(ServerRequest request) {
        return ServerResponse.ok()
                .body(shaurmaRepository.findAll().take(designProperties.getRecentSize()), Shaurma.class);
    }

    private Mono<ServerResponse> postShaurma(ServerRequest request) {
        Mono<Shaurma> shaurma = request.bodyToMono(Shaurma.class);
        Mono<Shaurma> savedShaurma = shaurmaRepository.saveAll(shaurma).next();
        return ServerResponse
                .created(URI.create(
                        "http://localhost:8080/rest/rx/router/design/shaurma" + savedShaurma.block().getId()
                ))
                .body(savedShaurma, Shaurma.class);
    }

}
