package ru.artemaa.shaurma.web.api.rx;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.artemaa.shaurma.Shaurma;
import ru.artemaa.shaurma.data.jpa.rx.ShaurmaRepository;
import ru.artemaa.shaurma.web.DesignProperties;

@RestController
@RequestMapping(path = DesignShaurmaRxController.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DesignShaurmaRxController {
    public static final String BASE_URL = "/rest/rx/design";

    private final ShaurmaRepository shaurmaRepository;
    private final DesignProperties designProperties;

    public DesignShaurmaRxController(ShaurmaRepository shaurmaRepository, DesignProperties designProperties) {
        this.shaurmaRepository = shaurmaRepository;
        this.designProperties = designProperties;
    }

    @GetMapping("/recent")
    public Flux<Shaurma> recent() {
        return shaurmaRepository.findAll().take(designProperties.getRecentSize());
    }

    @GetMapping("/{id}")
    public Mono<Shaurma> getById(@PathVariable("id") Long id) {
        return shaurmaRepository.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Shaurma> postShaurma(@RequestBody Mono<Shaurma> shaurmaMono) {
        return shaurmaRepository.saveAll(shaurmaMono).next();
    }
}
