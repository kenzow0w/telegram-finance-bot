package ru.telegram.service.handler;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.TelegramBot;
import ru.telegram.service.ExpensesServiceImpl;
import ru.telegram.service.IncomeServiceImpl;
import ru.telegram.utils.CommandEnum;

@Component
public class InfoHandler implements CommandHandler{
    @Lazy
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private IncomeServiceImpl incomeService;
    @Autowired
    private ExpensesServiceImpl expensesService;


    @SneakyThrows
    @Override
    public void handle(Message msg) {
        User user = msg.getFrom();
        Long userId = user.getId();
        String text = "<b> " + user.getLastName() + " " + user.getFirstName() + " </b>\n\n";
//        int totalSum = incomeService.balance(userId);
//        text += "<b>Ваш баланс</b>: " + totalSum + " руб.\n";
//        int uotSum = expensesService.getAmount(userId);
//        BigDecimal outSum = repository.getOutSum(userId);
//        text += "<b>Расход</b>: " + outSum + " руб.\n";
        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.enableHtml(true);
        message.setText(text);
        telegramBot.execute(message);
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.INFO.name);
    }
}
