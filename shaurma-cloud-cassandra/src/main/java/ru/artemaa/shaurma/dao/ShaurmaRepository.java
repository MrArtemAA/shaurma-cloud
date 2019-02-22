package ru.artemaa.shaurma.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.artemaa.shaurma.model.Shaurma;

import java.util.UUID;

public interface ShaurmaRepository extends ReactiveCrudRepository<Shaurma, UUID> {
}
