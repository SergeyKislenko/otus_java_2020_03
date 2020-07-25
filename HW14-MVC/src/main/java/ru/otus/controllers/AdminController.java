package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    private final DBServiceUser userService;

    public AdminController(DBServiceUser userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/admin"})
    public String userListView(Model model) {
        Optional<List<User>> allUsers = userService.findAll();
        List<User> users = new ArrayList<>();
        allUsers.ifPresent(users::addAll);
        model.addAttribute("users", users);
        return "adminPanel.html";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user) {
        userService.saveUser(user);
        return new RedirectView("/", true);
    }
}
