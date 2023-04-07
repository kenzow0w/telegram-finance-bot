package ru.telegram.service.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CommandHandler {
    void handle(Message msg);
    boolean isMatch(String msgText);
    SendMessage sendInlineKeyBoardMessage(long chatId);
}
