package ru.psu.fvt.moipevm.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psu.fvt.moipevm.taskmanager.model.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
