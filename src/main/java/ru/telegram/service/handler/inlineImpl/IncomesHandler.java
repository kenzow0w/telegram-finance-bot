package ru.telegram.service.handler.inlineImpl;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.telegram.config.BotConfig;
import ru.telegram.controller.IncomeController;
import ru.telegram.controller.TelegramBot;
import ru.telegram.service.handler.CommandHandler;
import ru.telegram.service.handler.InlineHandler;
import ru.telegram.utils.CommandEnum;
import ru.telegram.utils.InlineEnum;
import ru.telegram.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component
public class IncomesHandler implements InlineHandler {

    @Lazy
    @Autowired
    TelegramBot telegramBot;

    @Autowired
    Utils utils;

    @Autowired
    IncomeController incomeController;

    @Autowired
    List<CommandHandler> commandHandlers;

    @Override
    public boolean isMatch(String msg) {
        return msg.contains(InlineEnum.INCOMES.name);
    }

    @Override
    public void handle(CallbackQuery query) {
        User user = query.getFrom();
        if (query.getData().equals("incomes_add")) {
            telegramBot.sendMessage(sendInlineKeyBoardCategory(user.getId()));
            utils.getOperation().setLastCommand("incomes_add");
            BotConfig.STASH.put(user.getId(), utils.getOperation());
        } else if (query.getData().equals("incomes_for_month")) {

        } else if (query.getData().contains("incomes_cat_")) {
            utils.getOperation().setCategory(query.getData().replaceAll("^.{0,12}", ""));
            telegramBot.sendMessage(user.getId(), "Введите сумму");
            utils.getOperation().setLastCommand("inc_input_amount");
            BotConfig.STASH.put(user.getId(), utils.getOperation());
        } else {
            telegramBot.sendMessage(sendInlineKeyBoardMessage(user.getId()));
        }
    }

    public void handle(Message message) {
        long chatId = message.getFrom().getId();
        User user = message.getFrom();
        if (utils.getOperation().getLastCommand().equals("inc_input_amount")) {
            utils.getOperation().setAmount(Double.valueOf(message.getText()));
            incomeController.save(chatId, BotConfig.STASH.get(chatId));
            telegramBot.sendMessage(chatId, "Доход внесен");
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
        inlineKeyboardButton1.setCallbackData("incomes_add");
        inlineKeyboardButton2.setText(EmojiParser.parseToUnicode("Доходы за месяц :moneybag:"));
        inlineKeyboardButton2.setCallbackData("incomes_for_month");
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
        message.setText("Доходы");
        message.setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }

    public SendMessage sendInlineKeyBoardCategory(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        inlineKeyboardButton1.setText(EmojiParser.parseToUnicode("Зарплата :money_face:"));
        inlineKeyboardButton1.setCallbackData("incomes_cat_salary");
        inlineKeyboardButton2.setText(EmojiParser.parseToUnicode("Проценты от вклада :moneybag:"));
        inlineKeyboardButton2.setCallbackData("incomes_cat_bank");
        inlineKeyboardButton3.setText(EmojiParser.parseToUnicode("Другое"));
        inlineKeyboardButton3.setCallbackData("incomes_cat_others");
        inlineKeyboardButton4.setText(EmojiParser.parseToUnicode(":arrow_backward: Назад"));
        inlineKeyboardButton4.setCallbackData("Назад");
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        keyboardButtonsRow3.add(inlineKeyboardButton3);
        keyboardButtonsRow4.add(inlineKeyboardButton4);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        inlineKeyboardMarkup.setKeyboard(rowList);
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите категорию дохода");
        message.setReplyMarkup(inlineKeyboardMarkup);
        return message;
    }
}
