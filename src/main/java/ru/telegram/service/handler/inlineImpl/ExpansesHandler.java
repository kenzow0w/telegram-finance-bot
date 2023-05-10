package ru.telegram.service.handler.inlineImpl;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.telegram.config.BotConfig;
import ru.telegram.controller.ExpansesController;
import ru.telegram.controller.TelegramBot;
import ru.telegram.controller.UserController;
import ru.telegram.service.handler.CommandHandler;
import ru.telegram.service.handler.InlineHandler;
import ru.telegram.utils.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpansesHandler implements InlineHandler {

    @Lazy
    @Autowired
    TelegramBot telegramBot;

    @Autowired
    List<CommandHandler> commandHandlers;

    @Autowired
    UserController userController;

    @Autowired
    ExpansesController expansesController;

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
            telegramBot.sendMessage(sendInlineKeyBoardCategory(user.getId()));
            utils.getOperation().setLastCommand("expanses_add");
            BotConfig.STASH.put(user.getId(), utils.getOperation());
        } else if (query.getData().equals("expanses_for_month")) {
            utils.getOperation().setLastCommand("expanses_for_month");
            BotConfig.STASH.put(user.getId(), utils.getOperation());
            expansesController.findAllForLastMonth(user.getId());
        } else if (query.getData().contains("expanses_cat_")) {
            utils.getOperation().setCategory(query.getData().replaceAll("^.{0,13}", ""));
            telegramBot.sendMessage(user.getId(), "Введите сумму");
            utils.getOperation().setLastCommand("exp_input_amount");
            BotConfig.STASH.put(user.getId(), utils.getOperation());
        } else {
            telegramBot.sendMessage(sendInlineKeyBoardMessage(user.getId()));
        }
    }

    public void handle(Message message) {
        long chatId = message.getFrom().getId();
        User user = message.getFrom();
        if (utils.getOperation().getLastCommand().equals("exp_input_amount")) {
            utils.getOperation().setAmount(Double.valueOf(message.getText()));
            expansesController.save(chatId, BotConfig.STASH.get(chatId));
            telegramBot.sendMessage(chatId, "Затраты внесены");
            utils.getOperation().setLastCommand("wait");
            telegramBot.sendMessage(commandHandlers.stream().filter(c -> c.isMatch(CommandEnum.START.name)).findFirst().get().sendInlineKeyBoardMessage(chatId));
        } else if (false) {

        } else if (false) {

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
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Расходы");
        message.setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }

    public SendMessage sendInlineKeyBoardCategory(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode("Продукты :money_face:"));
        inlineKeyboardButton1.setCallbackData("expanses_cat_Продукты");
        inlineKeyboardButton2.setText(EmojiParser.parseToUnicode("Развлечения :money_face:"));
        inlineKeyboardButton2.setCallbackData("expanses_cat_Развлечения");
        inlineKeyboardButton3.setText(EmojiParser.parseToUnicode("Транспорт"));
        inlineKeyboardButton3.setCallbackData("expanses_cat_Транспорт");
        inlineKeyboardButton4.setText(EmojiParser.parseToUnicode("Спорт"));
        inlineKeyboardButton4.setCallbackData("expanses_cat_Спорт");
        inlineKeyboardButton5.setText(EmojiParser.parseToUnicode("Кафе"));
        inlineKeyboardButton5.setCallbackData("expanses_cat_Кафе");
        inlineKeyboardButton6.setText(EmojiParser.parseToUnicode("Досуг"));
        inlineKeyboardButton6.setCallbackData("expanses_cat_Досуг");
        inlineKeyboardButton7.setText(EmojiParser.parseToUnicode(":arrow_backward: Назад"));
        inlineKeyboardButton7.setCallbackData("Назад");
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);
        keyboardButtonsRow3.add(inlineKeyboardButton5);
        keyboardButtonsRow3.add(inlineKeyboardButton6);
        keyboardButtonsRow4.add(inlineKeyboardButton7);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите категорию расхода");
        message.setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }
}
