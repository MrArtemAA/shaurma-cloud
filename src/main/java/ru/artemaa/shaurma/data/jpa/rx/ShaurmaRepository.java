package ru.artemaa.shaurma.data.jpa.rx;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.artemaa.shaurma.Shaurma;

public interface ShaurmaRepository extends ReactiveCrudRepository<Shaurma, Long> {
}
