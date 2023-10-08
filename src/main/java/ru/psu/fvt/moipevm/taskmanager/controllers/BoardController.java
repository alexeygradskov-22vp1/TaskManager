package ru.psu.fvt.moipevm.taskmanager.controllers;

import jakarta.jws.soap.SOAPBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.psu.fvt.moipevm.taskmanager.exceptions.CreateException;
import ru.psu.fvt.moipevm.taskmanager.exceptions.DeleteException;
import ru.psu.fvt.moipevm.taskmanager.exceptions.GetDataException;
import ru.psu.fvt.moipevm.taskmanager.forms.BoardForm;
import ru.psu.fvt.moipevm.taskmanager.model.Board;
import ru.psu.fvt.moipevm.taskmanager.model.User;
import ru.psu.fvt.moipevm.taskmanager.services.BoardServiceImpl;
import ru.psu.fvt.moipevm.taskmanager.services.UserServiceImpl;
import ru.psu.fvt.moipevm.taskmanager.transfers.ResponseTransfer;

import java.util.List;
import java.util.Objects;

@Controller
public class BoardController {
    private final BoardServiceImpl boardService;
    private final UserServiceImpl userService;


    public BoardController(BoardServiceImpl boardService, UserServiceImpl userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @RequestMapping("/main")
    public String getMainPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin(auth.getName());
        List<Board> boards = boardService.getBoardsByIdOfUser(user.getId());
        model.addAttribute("boards", boards);
        model.addAttribute("user", user);
        return "main";
    }

    @PostMapping("/boards/create")
    @ResponseBody
    public ResponseTransfer createBoard(@RequestBody BoardForm boardForm) {
        Board board = new Board();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin(authentication.getName());
        board.setIdOfUser(user.getId());
        board.setName(boardForm.getName());
        board.setDescription(boardForm.getDescription());
        try {
            boardService.create(board);
        } catch (CreateException e) {
            return new ResponseTransfer("board don't created");
        }
        return new ResponseTransfer("board created");
    }

    @GetMapping("/boards/delete")
    public String deleteBoard(@RequestBody Integer boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin(authentication.getName());
        try {
            Board board = boardService.read(boardId);
            if (Objects.equals(board.getIdOfUser(), user.getId())){
                boardService.delete(boardId);
            }else {
                return "redirect:/pageNotFound";
            }
        } catch (DeleteException | GetDataException e) {
            return "redirect:/pageNotFound";
        }
        return "redirect:/main";
    }

    @GetMapping("/boards/{boardID}")
    public String getBoardPage(@PathVariable("boardID") Integer boardId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin(authentication.getName());
        Board board;
        try {
            board = boardService.read(boardId);
        } catch (GetDataException e) {
            return "redirect:/pageNotFound";
        }
        if (!Objects.equals(user.getId(), board.getIdOfUser())) {
            return "redirect:/pageNotFound";
        }
        model.addAttribute("board", board);
        return "board_page";
    }

}
