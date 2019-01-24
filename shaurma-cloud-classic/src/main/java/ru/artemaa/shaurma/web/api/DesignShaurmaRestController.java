package ru.artemaa.shaurma.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemaa.shaurma.data.jpa.ShaurmaRepository;
import ru.artemaa.shaurma.entity.jpa.Shaurma;
import ru.artemaa.shaurma.web.DesignProperties;
import ru.artemaa.shaurma.web.api.hateoas.ShaurmaResource;
import ru.artemaa.shaurma.web.api.hateoas.ShaurmaResourceAssembler;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/rest/design", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class DesignShaurmaRestController {
    private final ShaurmaRepository shaurmaRepository;
    private final ShaurmaResourceAssembler resourceAssembler;

    private DesignProperties designProperties;

    public DesignShaurmaRestController(ShaurmaRepository shaurmaRepository, ShaurmaResourceAssembler resourceAssembler) {
        this.shaurmaRepository = shaurmaRepository;
        this.resourceAssembler = resourceAssembler;
    }

    @Autowired
    public void setDesignProperties(DesignProperties designProperties) {
        this.designProperties = designProperties;
    }

    @GetMapping("/recent")
    public Resources<ShaurmaResource> getRecent() {
        Pageable pageable = PageRequest.of(
                0,
                designProperties.getRecentSize(),
                Sort.by("createdAt").descending()
        );

        List<Shaurma> shaurmas = shaurmaRepository.findAll(pageable).getContent();

        //Resources<Resource<Shaurma>> resources = Resources.wrap(shaurmas);
        List<ShaurmaResource> shaurmaResources = resourceAssembler.toResources(shaurmas);
        Resources<ShaurmaResource> resources = new Resources<>(shaurmaResources);

        resources.add(
                ControllerLinkBuilder
                        //.linkTo(DesignShaurmaRestController.class)
                        //.slash("recent")
                        .linkTo(methodOn(DesignShaurmaRestController.class).getRecent())
                        .withRel("recent")
        );


        return resources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shaurma> getById(@PathVariable("id") Long id) {
        return shaurmaRepository.findById(id)
                .map(shaurma -> new ResponseEntity<>(shaurma, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Shaurma postShaurma(@RequestBody Shaurma shaurma) {
        return shaurmaRepository.save(shaurma);
    }
}
