package ru.telegram.service.handler.inlineImpl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.telegram.service.handler.InlineHandler;
import ru.telegram.utils.InlineEnum;

@Component
public class IncomesHandler implements InlineHandler {
    @Override
    public boolean isMatch(String msg) {
        return msg.equals(InlineEnum.INCOMES.name);
    }

    @Override
    public void handle(CallbackQuery query) {
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId) {
        return null;
    }
}
