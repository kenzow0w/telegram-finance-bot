package ru.telegram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.telegram.config.BotConfig;
import ru.telegram.service.handler.CommandHandler;
import ru.telegram.service.handler.InlineHandler;
import ru.telegram.utils.CommandEnum;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static Logger LOG = LoggerFactory.getLogger(TelegramBot.class);

    final BotConfig config;

    @Autowired
    List<CommandHandler> commandHandlers;
    @Autowired
    List<InlineHandler> inlineHandlers;

    @Autowired
    UpdateController updateController;
    @Autowired
    UserController userController;
    @Autowired
    IncomeController incomeController;
    @Autowired
    ExpensesController expensesController;

    public InlineKeyboardMarkup inlineKeyboardMarkup;


    public TelegramBot(BotConfig config, UpdateController updateController) {
        this.config = config;
        this.updateController = updateController;
        this.inlineKeyboardMarkup = null;
    }

    @PostConstruct
    public void init() throws TelegramApiException {
        updateController.registerBot(this);
        List<BotCommand> commands = List.of(
                new BotCommand(CommandEnum.START.name, "Начало..."),
                new BotCommand(CommandEnum.INFO.name, "Информация..."),
                new BotCommand(CommandEnum.DELETE.name, "Удалить последнюю операцию..."),
                new BotCommand(CommandEnum.HISTORY.name, "История операций..."),
                new BotCommand(CommandEnum.HELP.name, "Помощь...")
        );
        this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
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
    public void onUpdateReceived(Update update) {
        updateController.processUpdate(update);
    }

    public void sendMessage(Long chatId, String message) {
        try {
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId.toString())
                    .text(message)
                    .build();
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOG.error(String.format("Sending message error: %s", e.getMessage()));
        }
    }


    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOG.error(String.format("Sending message error: %s", e.getMessage()));
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
//        keyboardFirstRow.add(Command.START.name());
//        keyboardFirstRow.add(Command.CLEAR.name());

        KeyboardRow keyboard2Row = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
//        keyboard2Row.add(Command.SHOW_EXPANSES.getDescription());
//        keyboard2Row.add(Command.BALANCE.getDescription());
//        keyboard2Row.add(Command.SHOW_INCOME.getDescription());

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
}
