package ru.telegram.service.handler.imp;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.Bot;
import ru.telegram.repository.UserOperationEntityRepository;
import ru.telegram.service.handler.CommandEnum;
import ru.telegram.service.handler.CommandHandler;

@Component
public class DeleteHandler implements CommandHandler {
    @Lazy
    @Autowired
    private Bot bot;
    @Autowired
    private UserOperationEntityRepository repository;

    @SneakyThrows
    @Override
    public void handle(Message msg) {
        User user = msg.getFrom();
        Long userId = user.getId();
        repository.deleteLast(userId);
        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.setText("Последняя операция удалена!");
        bot.execute(message);
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.DELETE.name);
    }
}
