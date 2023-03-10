package ru.telegram.service.handler.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.Bot;
import ru.telegram.entity.UserOperationEntity;
import ru.telegram.repository.UserEntityRepository;
import ru.telegram.repository.UserOperationEntityRepository;
import ru.telegram.service.handler.CommandEnum;
import ru.telegram.service.handler.CommandHandler;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class OperationHandler implements CommandHandler {
    @Lazy
    @Autowired
    private Bot bot;
    @Autowired
    private UserOperationEntityRepository repository;
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public void handle(Message msg) {
        User user = msg.getFrom();
        Long userId = user.getId();
        String msgText = msg.getText();
        try {
            String[] parts = msgText.split(" ");
            if(parts.length != 3) {
                throw new Exception("Больше 2 аргументов!");
            }
            BigDecimal sum = new BigDecimal(parts[1]);
            String category = parts[2];
            UserOperationEntity entity = new UserOperationEntity();
            entity.setSum(sum);
            entity.setCategory(category);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUser(userEntityRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
            repository.saveAndFlush(entity);
            bot.sendText(userId, "Операция на сумму => " + sum + " руб. по категории => " + category);
        } catch (Exception ex) {
            bot.sendText(userId, """
                    Невалидная операция!
                    Операция должна иметь формат /operation сумма категория
                    """);
        }
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.contains(CommandEnum.OPERATION.name);
    }
}
