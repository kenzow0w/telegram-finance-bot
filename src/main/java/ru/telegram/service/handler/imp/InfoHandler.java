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

import java.math.BigDecimal;

@Component
public class InfoHandler implements CommandHandler {
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
        String text = "<b> " + user.getLastName() + " " + user.getFirstName() + " </b>\n\n";
        BigDecimal totalSum = repository.getTotalSum(userId);
        text += "<b>Ваш баланс</b>: " + totalSum + " руб.\n";
        BigDecimal inSum = repository.getInSum(userId);
        text += "<b>Доход</b>: " + inSum + " руб.\n";
        BigDecimal outSum = repository.getOutSum(userId);
        text += "<b>Расход</b>: " + outSum + " руб.\n";
        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.enableHtml(true);
        message.setText(text);
        bot.execute(message);
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.INFO.name);
    }
}
