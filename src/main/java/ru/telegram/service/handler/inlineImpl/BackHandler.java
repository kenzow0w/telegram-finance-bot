package ru.telegram.service.handler.inlineImpl;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.telegram.controller.TelegramBot;
import ru.telegram.service.handler.InlineHandler;
import ru.telegram.utils.InlineEnum;
import ru.telegram.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component
public class BackHandler implements InlineHandler {

    @Lazy
    @Autowired
    TelegramBot telegramBot;

    @Autowired
    Utils utils;

    @Override
    public boolean isMatch(String query) {
        return query.equals(InlineEnum.BACK.name);
    }

    @Override
    public void handle(CallbackQuery query) {
        utils.getOperation().setLastCommand("wait");
        telegramBot.sendMessage(sendInlineKeyBoardMessage(query.getFrom().getId()));
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId) {
        telegramBot.inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode("Расходы :money_face:"));
        inlineKeyboardButton1.setCallbackData("Расходы");
        inlineKeyboardButton2.setText(EmojiParser.parseToUnicode("Доходы :moneybag:"));
        inlineKeyboardButton2.setCallbackData("Доходы");
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        telegramBot.inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите следующие действие:");
        message.setReplyMarkup(telegramBot.inlineKeyboardMarkup);
        return message;
    }
}
