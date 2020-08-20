package ru.list.surkovr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.list.surkovr.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByActivationCode(String code);

    User findByUsername(String username);
}
