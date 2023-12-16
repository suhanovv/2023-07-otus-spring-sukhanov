package ru.otus.homework.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
