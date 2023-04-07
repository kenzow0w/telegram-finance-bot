package ru.telegram.service.handler.commandImpl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.TelegramBot;
import ru.telegram.service.handler.CommandHandler;
import ru.telegram.utils.CommandEnum;

@Component
public class HelpHandler implements CommandHandler {

    @Lazy
    @Autowired
    private TelegramBot telegramBot;

    @SneakyThrows
    @Override
    public void handle(Message msg) {
        User user = msg.getFrom();
        Long userId = user.getId();
        String text = "Я твой персональный помощник для подсчета расходов и доходов.\n" +
                "Ты можешь использовать следующие команды:\n /start - начать работу со мной,\n" +
                " /report - для получения отчета о расходах и" +
                " доходах,\n /info - для просмотра информации о балансе" +
                " о балансе\n";
        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.enableHtml(true);
        message.setText(text);
        telegramBot.execute(message);
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.HELP.name);
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId) {
        return null;
    }
}
