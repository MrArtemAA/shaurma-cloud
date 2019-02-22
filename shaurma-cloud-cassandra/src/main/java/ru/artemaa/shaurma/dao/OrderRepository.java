package ru.artemaa.shaurma.dao;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import ru.artemaa.shaurma.model.Order;

import java.util.UUID;

public interface OrderRepository extends ReactiveCassandraRepository<Order, UUID> {
}
