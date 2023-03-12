package ru.telegram.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.TelegramBot;
import ru.telegram.controller.UserController;
import ru.telegram.utils.CommandEnum;

@Component
public class StartHandler implements CommandHandler {

    @Lazy
    @Autowired
    TelegramBot telegramBot;

    @Autowired
    UserController userController;

    @Override
    public void handle(Message msg) {
        User user = userController.getOne(msg.getFrom().getId()) != null ? userController.getOne(msg.getFrom().getId()) : userController.save(msg.getFrom());
        telegramBot.sendMessage(msg.getFrom().getId(), "Hi " + msg.getFrom().getUserName());
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.START.name);
    }
}
