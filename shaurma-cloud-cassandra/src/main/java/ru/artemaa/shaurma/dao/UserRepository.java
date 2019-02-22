package ru.artemaa.shaurma.dao;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;
import ru.artemaa.shaurma.model.User;

import java.util.UUID;

public interface UserRepository extends ReactiveCassandraRepository<User, UUID> {
    @AllowFiltering
    Mono<User> findByUsername(String username);
}
