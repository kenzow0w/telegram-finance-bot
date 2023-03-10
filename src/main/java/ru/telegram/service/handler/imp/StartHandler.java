package ru.telegram.service.handler.imp;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.Bot;
import ru.telegram.entity.UserEntity;
import ru.telegram.repository.UserEntityRepository;
import ru.telegram.service.handler.CommandEnum;
import ru.telegram.service.handler.CommandHandler;

import java.time.LocalDateTime;

@Component
public class StartHandler implements CommandHandler {
    @Lazy
    @Autowired
    private Bot bot;
    @Autowired
    private UserEntityRepository repository;

    @Override
    public void handle(Message msg) {
        User user = msg.getFrom();
        Long userId = user.getId();
        if(repository.findById(userId).isEmpty()) {
            UserEntity entity = new UserEntity();
            entity.setId(userId);
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setUserName(user.getUserName());
            entity.setStartedAt(LocalDateTime.now());
            repository.saveAndFlush(entity);
        }
        String text = EmojiParser.parseToUnicode("Добрый день, " + user.getFirstName() + ", начнем подсчитывать ваш бюджет!" + " :moneybag:");
        bot.sendText(userId, text);
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.START.name);
    }
}
