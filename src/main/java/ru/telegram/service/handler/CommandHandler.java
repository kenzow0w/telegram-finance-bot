package ru.telegram.service.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandHandler {
    void handle(Message msg);
    boolean isMatch(String msgText);
}
