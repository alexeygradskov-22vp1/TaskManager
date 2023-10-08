package ru.psu.fvt.moipevm.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.psu.fvt.moipevm.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    public boolean existsTaskByTaskName(String name);


    @Query("""
                select count(Task) > 0 from Task
                join Card c on c.id = Task.idOfCards
                join Board b on b.id = c.idOfBoard
                where b.idOfUser = :userId
            """)
    public boolean existsTaskByUserId(
            @Param("userId") Integer id
    ); // return true/false; true - user task / false other user task and not access
}
