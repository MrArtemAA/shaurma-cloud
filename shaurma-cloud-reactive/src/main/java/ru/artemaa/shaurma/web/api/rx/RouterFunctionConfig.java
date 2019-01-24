package ru.artemaa.shaurma.web.api.rx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.artemaa.shaurma.Shaurma;
import ru.artemaa.shaurma.data.jpa.rx.ShaurmaRepository;
import ru.artemaa.shaurma.web.DesignProperties;

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
        return ServerResponse.ok()
                .body(shaurmaRepository.saveAll(shaurma).next(), Shaurma.class);
    }

}
