package ru.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.config.BotConfig;
import ru.telegram.service.handler.inlineImpl.ExpansesHandler;
import ru.telegram.service.handler.inlineImpl.IncomesHandler;
import ru.telegram.utils.Utils;

import java.util.Arrays;

@Component
public class UpdateController {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateController.class);

    @Lazy
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    ExpansesController expansesController;

    @Autowired
    Utils utils;

    @Autowired
    ExpansesHandler expansesHandler;

    @Autowired
    IncomesHandler incomesHandler;

    private Message message;

    private String[] expOperations = {"exp_chose_category", "exp_input_amount"};
    private String[] incOperations = {"inc_chose_category", "inc_input_amount"};

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update.getMessage() != null) {
            message = update.getMessage();
            long chatId = message.getChatId();
            if (update.getMessage().hasText()) {
                if (message.getText().startsWith("/")) {
                    for (var commandHandler : telegramBot.commandHandlers) {
                        if (commandHandler.isMatch(message.getText())) {
                            LOG.info(String.format("Getting a message '%s' from '%s'", update.getMessage().getText(), update.getMessage().getFrom().getUserName()));
                            commandHandler.handle(message);
                            return;
                        }
                    }
                } else if (Arrays.asList(expOperations).contains(utils.getOperation().getLastCommand())) {
                    expansesHandler.handle(message);
                } else if (Arrays.asList(incOperations).contains(utils.getOperation().getLastCommand())) {
                    incomesHandler.handle(message);
                } else {
                    telegramBot.sendMessage(chatId, "Операция не поддерживается!");
                }
            } else {
                telegramBot.sendMessage(chatId, "This command doesn't supported");
            }
        } else if (update.hasCallbackQuery()) {
            for (var inlineHandler : telegramBot.inlineHandlers) {
                if (inlineHandler.isMatch(update.getCallbackQuery().getData())) {
                    LOG.info(String.format("Getting a CallBackQuery '%s' from '%s'", update.getCallbackQuery().getData(), update.getCallbackQuery().getFrom().getUserName()));
                    inlineHandler.handle(update.getCallbackQuery());
                    return;
                }
            }
        } else {
            LOG.error("Received null message");
            return;
        }
    }
}