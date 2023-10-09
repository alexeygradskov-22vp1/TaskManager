package ru.psu.fvt.moipevm.taskmanager.services;

import jakarta.xml.ws.http.HTTPException;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.psu.fvt.moipevm.taskmanager.exceptions.CreateException;
import ru.psu.fvt.moipevm.taskmanager.exceptions.DeleteException;
import ru.psu.fvt.moipevm.taskmanager.exceptions.GetDataException;
import ru.psu.fvt.moipevm.taskmanager.model.Board;
import ru.psu.fvt.moipevm.taskmanager.model.User;
import ru.psu.fvt.moipevm.taskmanager.repositories.BoardRepository;

import java.util.List;

@Service
public class BoardServiceImpl implements ITaskService<Board> {
    private final BoardRepository boardRepository;


    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public String create(Board obj) throws CreateException {
        if (boardRepository.existsBoardByName(obj.getName())) {
            throw new CreateException("A board with that name already exists") ;
        }
        else if(obj.getName().isEmpty()){
            throw new CreateException("Name not be null");
        }
        boardRepository.save(obj);
        return "board be created";
    }

    @Override
    public List<Board> readAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board read(int id) throws GetDataException {
        if (!boardRepository.existsById(id)){
            throw new GetDataException("Board not found");
        }
        return boardRepository.getOne(id);
    }


    public List<Board> getBoardsByIdOfUser(int idOfUser) {
        return boardRepository.getBoardsByIdOfUser(idOfUser);
    }


    @Override
    public void update(Board board) {
        boardRepository.save(board);
    }


    @Override
    public void delete(int id, int userId) throws DeleteException {
        if (!boardRepository.existsBoardByUserId(userId)) throw new DeleteException("No access!");

        if (!boardRepository.existsById(id)) {
            throw new DeleteException("Board don't deleted");
        }
        boardRepository.deleteById(id);
    }
}

