package ru.artemaa.shaurma.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.artemaa.shaurma.Shaurma;
import ru.artemaa.shaurma.data.jpa.ShaurmaRepository;
import ru.artemaa.shaurma.web.DesignProperties;

@RestController
@RequestMapping(path = "/design", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class DesignShaurmaRestController {
    private final ShaurmaRepository shaurmaRepository;

    private DesignProperties designProperties;

    public DesignShaurmaRestController(ShaurmaRepository shaurmaRepository) {
        this.shaurmaRepository = shaurmaRepository;
    }

    @Autowired
    public void setDesignProperties(DesignProperties designProperties) {
        this.designProperties = designProperties;
    }

    @GetMapping("/recent")
    public Iterable<Shaurma> getRecent() {
        Pageable pageable = PageRequest.of(0, designProperties.getRecentSize(), Sort.by("createdAt").descending());
        return shaurmaRepository.findAll(pageable).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shaurma> getById(@PathVariable("id") Long id) {
        return shaurmaRepository.findById(id)
                .map(shaurma -> new ResponseEntity<>(shaurma, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
