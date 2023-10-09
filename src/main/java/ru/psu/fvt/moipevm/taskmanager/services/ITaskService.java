package ru.psu.fvt.moipevm.taskmanager.services;

import ru.psu.fvt.moipevm.taskmanager.exceptions.CreateException;
import ru.psu.fvt.moipevm.taskmanager.exceptions.DeleteException;

import java.util.List;

public interface ITaskService<T> {
    String create(T obj) throws CreateException;

    List<T> readAll();

    T read(int id) throws Exception;

    void update(T obj);

    void delete(int id, int userId) throws DeleteException;
}
