package ru.psu.fvt.moipevm.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.psu.fvt.moipevm.taskmanager.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByLogin(String login);
    boolean existsUserByLogin(String login);
}
