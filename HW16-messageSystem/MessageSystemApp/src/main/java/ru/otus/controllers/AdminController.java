package ru.otus.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.front.FrontendService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    private final DBServiceUser userService;
    private final FrontendService frontendService;
    private final SimpMessagingTemplate template;

    public AdminController(DBServiceUser userService, FrontendService frontendService, SimpMessagingTemplate template) {
        this.userService = userService;
        this.frontendService = frontendService;
        this.template = template;
    }

    @GetMapping({"/", "/admin"})
    public String userListView(Model model) {
        Optional<List<User>> allUsers = userService.findAll();
        List<User> users = new ArrayList<>();
        allUsers.ifPresent(users::addAll);
        model.addAttribute("users", users);
        return "adminPanel.html";
    }

    @MessageMapping("/user/create")
    public void createUser(User user) {
        frontendService.saveUser(user, this::userCreated);
    }

    public void userCreated(User user) {
        template.convertAndSend("/topic/refresh", user);
    }
}
