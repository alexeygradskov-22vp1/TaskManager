package ru.psu.fvt.moipevm.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.psu.fvt.moipevm.taskmanager.model.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    boolean existsBoardByName(String name);
    List<Board> getBoardsByIdOfUser(int id);

    @Query("""
            select count(Board) > 0 from Board
            where Board.idOfUser = 1
                """)
    public boolean existsBoardByUserId(
            @Param("userId") Integer id
    ); // return true/false; true - user board / false other user board and not access
}
