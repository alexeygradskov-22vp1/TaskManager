package ru.psu.fvt.moipevm.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psu.fvt.moipevm.taskmanager.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
