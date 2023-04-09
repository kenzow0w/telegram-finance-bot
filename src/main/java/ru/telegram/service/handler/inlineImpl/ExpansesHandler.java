package ru.telegram.service.handler.inlineImpl;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.telegram.controller.ExpansesController;
import ru.telegram.controller.TelegramBot;
import ru.telegram.controller.UserController;
import ru.telegram.service.handler.InlineHandler;
import ru.telegram.utils.InlineEnum;
import ru.telegram.utils.MappingUtils;
import ru.telegram.utils.Operation;
import ru.telegram.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ExpansesHandler implements InlineHandler {

    @Lazy
    @Autowired
    TelegramBot telegramBot;

    @Autowired
    UserController userController;

    @Autowired
    Utils utils;

    @Override
    public boolean isMatch(String msg) {
        return msg.contains(InlineEnum.EXPANSES.name);
    }

    @Override
    public void handle(CallbackQuery query) {
        User user = query.getFrom();
        if (query.getData().equals("expanses_add")) {
            telegramBot.sendMessage(user.getId(), "Введите категорию расхода");
            utils.getOperation().setLastCommand("expanses_add");
            MappingUtils.getSTASH().put(user.getId(), utils.getOperation());
        } else if (query.getData().equals("expanses_for_month")) {

        } else {
            telegramBot.sendMessage(sendInlineKeyBoardMessage(user.getId()));
        }
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId) {
        telegramBot.inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode("Добавить :money_face:"));
        inlineKeyboardButton1.setCallbackData("expanses_add");
        inlineKeyboardButton2.setText(EmojiParser.parseToUnicode("Расходы за месяц :moneybag:"));
        inlineKeyboardButton2.setCallbackData("expanses_for_month");
        inlineKeyboardButton3.setText(EmojiParser.parseToUnicode(":arrow_backward: Назад"));
        inlineKeyboardButton3.setCallbackData("Назад");
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        keyboardButtonsRow3.add(inlineKeyboardButton3);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        telegramBot.inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Расходы");
        message.setReplyMarkup(telegramBot.inlineKeyboardMarkup);
        return message;
    }
}
