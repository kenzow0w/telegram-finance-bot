package ru.telegram.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.service.UserServiceImpl;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService){
        this.userService = userService;
    }

    public User getOne(long id) {
        return userService.getOne(id);
    }

    public User save(User user) {
        return  userService.save(user);
    }
}
