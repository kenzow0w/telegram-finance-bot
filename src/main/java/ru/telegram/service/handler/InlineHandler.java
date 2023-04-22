package ru.telegram.service.handler;

import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface InlineHandler {
    boolean isMatch(String query);
    void handle(CallbackQuery query) throws Exception;
    SendMessage sendInlineKeyBoardMessage(long chatId);
}
