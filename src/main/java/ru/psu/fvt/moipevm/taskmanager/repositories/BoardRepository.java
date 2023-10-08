package ru.psu.fvt.moipevm.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psu.fvt.moipevm.taskmanager.model.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    boolean existsBoardByName(String name);
    List<Board> getBoardsByIdOfUser(int id);
}
