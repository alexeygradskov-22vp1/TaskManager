package ru.psu.fvt.moipevm.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psu.fvt.moipevm.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    public boolean existsTaskByTaskName(String name);
}
