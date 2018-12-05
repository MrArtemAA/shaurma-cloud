package ru.artemaa.shaurma.data.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.artemaa.shaurma.Shaurma;

public interface ShaurmaRepository extends PagingAndSortingRepository<Shaurma, Long> {
}
