package ru.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.telegram.dao.ExpansesEntityRepository;
import ru.telegram.init.BotConfig;
import ru.telegram.utils.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static Logger LOG = LoggerFactory.getLogger(TelegramBot.class);

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBOT_NAME();
    }

    @Override
    public String getBotToken() {
        return config.getBOT_TOKEN();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.getMessage().hasText()) {
            return;
        }
        long chatId = update.getMessage().getChatId();
        String name = update.getMessage().getChat().getFirstName() + " " + update.getMessage().getChat().getLastName();
        String messageText = update.getMessage().getText();
        LOG.info(messageText + " " + name);
        switch (messageText) {
            case "/start":
                startCommandReceived(chatId, name);
                break;
            default:
                String com = Arrays.stream(Command.values()).filter(c -> c.getDescription().equals(messageText)).findFirst().toString();

        }
    }

    private void startCommandReceived(long id, String name) {
        String answer = "Hi " + name + " start to count your budget";
        greetingMessageWithMenu(id, answer);
    }

    private void greetingMessageWithMenu(long id, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText(textToSend);
        ReplyKeyboardMarkup replyKeyboardMarkup = getMenu();
        message.setReplyMarkup(replyKeyboardMarkup);
        message.setParseMode(ParseMode.HTML);
        message.disableWebPagePreview();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.getMessage();
        }
    }

    private ReplyKeyboardMarkup getMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var yesButton = new InlineKeyboardButton();
        yesButton.setText("yes");
        yesButton.setCallbackData("YES_BUTTON");
        rowInLine.add(yesButton);
        rowsInLine.add(rowInLine);
        inlineKeyboardMarkup.setKeyboard(rowsInLine);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(false);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(Command.ADD_EXPANSES.getDescription());
        keyboardFirstRow.add(Command.ADD_INCOME.getDescription());

        KeyboardRow keyboard2Row = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboard2Row.add(Command.SHOW_EXPANSES.getDescription());
        keyboard2Row.add(Command.BALANCE.getDescription());
        keyboard2Row.add(Command.SHOW_INCOME.getDescription());

        // Добавляем кнопки во третью строчку клавиатуры
//        KeyboardRow keyboard3Row = new KeyboardRow();

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboard2Row);
//        keyboard.add(keyboard3Row);
        // добавляем список клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }


    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }
}
