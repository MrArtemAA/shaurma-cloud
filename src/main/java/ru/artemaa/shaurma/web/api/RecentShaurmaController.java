package ru.artemaa.shaurma.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import ru.artemaa.shaurma.Shaurma;
import ru.artemaa.shaurma.data.jpa.ShaurmaRepository;
import ru.artemaa.shaurma.web.DesignProperties;
import ru.artemaa.shaurma.web.api.hateoas.ShaurmaResource;
import ru.artemaa.shaurma.web.api.hateoas.ShaurmaResourceAssembler;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class RecentShaurmaController {
    private final ShaurmaRepository shaurmaRepository;
    private final ShaurmaResourceAssembler shaurmaResourceAssembler;

    private DesignProperties designProperties;

    public RecentShaurmaController(ShaurmaRepository shaurmaRepository, ShaurmaResourceAssembler shaurmaResourceAssembler) {
        this.shaurmaRepository = shaurmaRepository;
        this.shaurmaResourceAssembler = shaurmaResourceAssembler;
    }

    @Autowired
    public void setDesignProperties(DesignProperties designProperties) {
        this.designProperties = designProperties;
    }

    @GetMapping(value = "/shaurmas/recent", produces = "application/hal+json")
    public ResponseEntity<Resources<ShaurmaResource>> recent() {
        Pageable pageable = PageRequest.of(
                0,
                designProperties.getRecentSize(),
                Sort.by("createdAt").descending()
        );

        List<Shaurma> shaurmas = shaurmaRepository.findAll(pageable).getContent();

        List<ShaurmaResource> shaurmaResources = shaurmaResourceAssembler.toResources(shaurmas);
        Resources<ShaurmaResource> resources = new Resources<>(shaurmaResources);

        resources.add(
                ControllerLinkBuilder
                        .linkTo(methodOn(RecentShaurmaController.class).recent())
                        .withRel("recents")
        );

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
