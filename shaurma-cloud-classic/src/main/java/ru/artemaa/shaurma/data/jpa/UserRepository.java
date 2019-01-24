package ru.artemaa.shaurma.data.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.artemaa.shaurma.entity.jpa.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
