package ru.psu.fvt.moipevm.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.psu.fvt.moipevm.taskmanager.exceptions.DeleteException;
import ru.psu.fvt.moipevm.taskmanager.model.Task;
import ru.psu.fvt.moipevm.taskmanager.repositories.TaskRepository;

import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService<Task> {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String create(Task obj) {
        taskRepository.save(obj);
        return "";
    }

    @Override
    public List<Task> readAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task read(int id) {
        return taskRepository.getOne(id);
    }

    @Override
    public void update(Task obj) {
        taskRepository.save(obj);

    }

    @Override
    public void delete(int id, int userId) throws DeleteException {
        if (!taskRepository.existsTaskByUserId(userId)) throw new DeleteException("no access to task");

        if (!taskRepository.existsById(id)) throw new DeleteException("Task don't created");

        taskRepository.deleteById(id);
    }
}
