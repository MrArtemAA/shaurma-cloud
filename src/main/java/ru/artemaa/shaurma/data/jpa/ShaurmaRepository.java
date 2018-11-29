package ru.artemaa.shaurma.data.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.artemaa.shaurma.Shaurma;

public interface ShaurmaRepository extends CrudRepository<Shaurma, Long> {
}
