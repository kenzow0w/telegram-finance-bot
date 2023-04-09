package ru.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.utils.MappingUtils;
import ru.telegram.utils.Utils;

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

    private Message message;

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
                } else if (utils.getOperation().getLastCommand().equals("expanses_add")) {
                    utils.getOperation().setLastCommand("exp_chose_category");
                    utils.getOperation().setCategory(message.getText());
                    telegramBot.sendMessage(chatId, "Введите сумму");
                } else if (utils.getOperation().getLastCommand().equals("exp_chose_category")) {
                    utils.getOperation().setLastCommand("input_amount");
                    utils.getOperation().setAmount(Double.valueOf(message.getText()));
                    expansesController.save(chatId, MappingUtils.getSTASH().get(chatId));
                    telegramBot.sendMessage(chatId, "Затраты внесены");
                    utils.getOperation().setLastCommand("wait");
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