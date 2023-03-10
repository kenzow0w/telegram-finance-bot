package ru.telegram.service.handler.imp;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.Bot;
import ru.telegram.entity.UserOperationEntity;
import ru.telegram.repository.UserOperationEntityRepository;
import ru.telegram.service.handler.CommandEnum;
import ru.telegram.service.handler.CommandHandler;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class HistoryHandler implements CommandHandler {
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
        List<UserOperationEntity> operations = repository.findAllByUserId(userId);
        String format = "| %4s | %20s | %10s | %20s |";
        StringBuilder builder = new StringBuilder(String.format(format, "ID", "Когда", "Сумма", "Категория"));
        for (var operation : operations) {
            builder.append("\n");
            builder.append(String.format(format,
                    operation.getId(),
                    operation.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                    operation.getSum(),
                    operation.getCategory()
            ));
        }
        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.enableHtml(true);
        message.setText("<pre>" + builder + "</pre>");
        bot.execute(message);
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.HISTORY.name);
    }
}
