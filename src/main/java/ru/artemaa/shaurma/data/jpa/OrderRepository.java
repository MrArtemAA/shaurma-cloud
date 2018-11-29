package ru.artemaa.shaurma.data.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.artemaa.shaurma.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
