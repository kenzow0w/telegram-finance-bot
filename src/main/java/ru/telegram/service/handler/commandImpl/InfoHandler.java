package ru.telegram.service.handler.commandImpl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.TelegramBot;
import ru.telegram.controller.UserController;
import ru.telegram.service.UserServiceImpl;
import ru.telegram.service.handler.CommandHandler;
import ru.telegram.utils.CommandEnum;
import ru.telegram.utils.MappingUtils;
import ru.telegram.utils.Operation;
import ru.telegram.utils.Utils;

import java.util.Map;

@Component
public class InfoHandler implements CommandHandler {
    @Lazy
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    Utils utils;

    @SneakyThrows
    @Override
    public void handle(Message msg) {
        User user = msg.getFrom();
        Long userId = user.getId();
        String text = "<b> " + user.getLastName() + " " + user.getFirstName() + " </b>\n\n";
        double totalSum = userService.getOne(userId).getBalance();
        text += "<b>Ваш баланс</b>: " + totalSum + " руб.\n";
//        int uotSum = expensesService.getAmount(userId);
//        BigDecimal outSum = repository.getOutSum(userId);
//        text += "<b>Расход</b>: " + outSum + " руб.\n";
        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.enableHtml(true);
        message.setText(text);
        utils.getOperation().setLastCommand("/info");
        MappingUtils.getSTASH().put(msg.getFrom().getId(), telegramBot.operation);
        telegramBot.execute(message);
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.INFO.name);
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId) {
        return null;
    }
}
