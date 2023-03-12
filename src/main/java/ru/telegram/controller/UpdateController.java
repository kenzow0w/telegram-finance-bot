package ru.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateController {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateController.class);

    @Lazy
    @Autowired
    private TelegramBot telegramBot;

    private Message message;

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (!update.getMessage().hasText()) {
            LOG.error("Received null message");
            return;
        }
        message = update.getMessage();
        long chatId = message.getChatId();
        if (message.getText().startsWith("/")) {
            for (var commandHandler : telegramBot.commandHandlers) {
                if (commandHandler.isMatch(message.getText())) {
                    commandHandler.handle(message);
                    return;
                }
            }
            telegramBot.sendMessage(chatId, "Операция не поддерживается!");
        } else {
            switch (message.getText()) {
                case "add income":

                    break;
                case "add expanse":

                    break;
                default:
                    telegramBot.sendMessage(chatId, "This command doesn't supported");
            }
        }
    }


}