package ru.telegram.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.entity.UserEntity;
import ru.telegram.service.UserServiceImpl;

import static ru.telegram.utils.MappingUtils.mapToUserEntity;

@Controller
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    public UserEntity getOne(long id) {
        return userService.getOne(id);
    }

    public User save(User user) {
        UserEntity userEntity = mapToUserEntity(user);
        userService.save(userEntity);
        return user;
    }
}
