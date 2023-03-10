package ru.telegram.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.telegram.service.handler.CommandEnum;
import ru.telegram.service.handler.CommandHandler;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private List<CommandHandler> commandHandlers;

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @PostConstruct
    private void initCommands() throws TelegramApiException {
        List<BotCommand> commands = List.of(
                new BotCommand(CommandEnum.START.name, "Начало..."),
                new BotCommand(CommandEnum.INFO.name, "Информация..."),
                new BotCommand(CommandEnum.OPERATION.name, "Операция..."),
                new BotCommand(CommandEnum.DELETE.name, "Удалить последнюю операцию..."),
                new BotCommand(CommandEnum.HISTORY.name, "История операций..."),
                new BotCommand(CommandEnum.HELP.name, "Помощь...")
        );
        this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        if (msg != null && msg.hasText()) {
            User user = msg.getFrom();
            Long userId = user.getId();
            String msgText = msg.getText();
            for (var commandHandler : commandHandlers) {
                if (commandHandler.isMatch(msgText)) {
                    commandHandler.handle(msg);
                    return;
                }
            }
            sendText(userId, "Операция не поддерживается!");
        }
    }

    @SneakyThrows
    public void sendText(Long userId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(userId);
        message.setText(text);
        execute(message);
    }
}
