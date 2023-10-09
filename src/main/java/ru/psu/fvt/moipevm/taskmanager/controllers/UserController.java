package ru.psu.fvt.moipevm.taskmanager.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.psu.fvt.moipevm.taskmanager.exceptions.DeleteException;
import ru.psu.fvt.moipevm.taskmanager.model.User;
import ru.psu.fvt.moipevm.taskmanager.services.UserServiceImpl;
import ru.psu.fvt.moipevm.taskmanager.transfers.ResponseIntegerValue;

import java.util.List;
import java.util.Objects;


@Controller
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) boolean error, Model model) {
        if (error) {
            model.addAttribute("isError", true);
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/deleteUser/{userID}")
    public String deleteUser(@PathVariable("userID") Integer userId) throws DeleteException {
        userService.delete(userId);
        return "redirect:/admin";
    }

    @GetMapping("/deleteAccount")
    private String deleteAccount(@RequestBody ResponseIntegerValue value) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByLogin(authentication.getName());
            if (Objects.equals(user.getId(), value.getValue())) {
                userService.delete(value.getValue());
            } else {
                return "redirect:/pageNotFound";
            }
        } catch (DeleteException e) {
            return "redirect:/pageNotFound";
        }
        return "redirect:/main";
    }


    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<User> users = userService.readAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/admin/blockUser/{userID}")
    public String blockUser(@PathVariable("userID") Integer userId) {
        userService.toggleUserBlock(userId);
        return "redirect:/admin";
    }
}

