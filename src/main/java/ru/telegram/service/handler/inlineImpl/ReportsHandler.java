package ru.telegram.service.handler.inlineImpl;

import com.vdurmont.emoji.EmojiParser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.telegram.config.BotConfig;
import ru.telegram.controller.TelegramBot;
import ru.telegram.service.handler.InlineHandler;
import ru.telegram.utils.InlineEnum;
import ru.telegram.utils.PieChartReport;
import ru.telegram.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReportsHandler implements InlineHandler {

    @Lazy
    @Autowired
    TelegramBot telegramBot;

    @Autowired
    Utils utils;

    @Autowired
    PieChartReport pieChartReport;

    @Override
    public boolean isMatch(String query) {
        return query.contains(InlineEnum.REPORTS.name);
    }

    @Override
    public void handle(CallbackQuery query) throws Exception {
        User user = query.getFrom();
        if (query.getData().equals("reports_for_month")) {
            utils.getOperation().setLastCommand("incomes_add");
            BotConfig.STASH.put(user.getId(), utils.getOperation());
        } else if (query.getData().equals("reports_for_year")) {

        } else if (query.getData().contains("incomes_cat_")) {
            utils.getOperation().setCategory(query.getData().replaceAll("^.{0,12}", ""));
            telegramBot.sendMessage(user.getId(), "Введите сумму");
            utils.getOperation().setLastCommand("inc_input_amount");
            BotConfig.STASH.put(user.getId(), utils.getOperation());
        } else {
            telegramBot.sendMessage(sendInlineKeyBoardMessage(user.getId()));
        }
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode("За месяц"));
        inlineKeyboardButton1.setCallbackData("reports_for_month");
        inlineKeyboardButton2.setText(EmojiParser.parseToUnicode("За год"));
        inlineKeyboardButton2.setCallbackData("reports_for_year");
        inlineKeyboardButton3.setText(EmojiParser.parseToUnicode(":arrow_backward: Назад"));
        inlineKeyboardButton3.setCallbackData("Назад");
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        keyboardButtonsRow3.add(inlineKeyboardButton3);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Отчет");
        message.setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }

    public SendPhoto sendPhoto(InputFile file) {
        SendPhoto picture = new SendPhoto();
        picture.setPhoto(file);
        try {
            telegramBot.execute(picture);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return picture;
    }
}
