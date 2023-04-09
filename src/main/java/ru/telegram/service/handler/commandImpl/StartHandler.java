package ru.telegram.service.handler.commandImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import com.vdurmont.emoji.EmojiParser;
import ru.telegram.controller.TelegramBot;
import ru.telegram.controller.UserController;
import ru.telegram.service.handler.CommandHandler;
import ru.telegram.utils.CommandEnum;
import ru.telegram.utils.MappingUtils;
import ru.telegram.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartHandler implements CommandHandler {

    private static Logger LOG = LoggerFactory.getLogger(StartHandler.class);

    @Lazy
    @Autowired
    TelegramBot telegramBot;

    @Autowired
    UserController userController;

    @Autowired
    Utils utils;

    @Override
    public void handle(Message msg) {
        User user = userController.getOne(msg.getFrom().getId()) != null ? MappingUtils.mapToUser(userController.getOne(msg.getFrom().getId())) : userController.save(msg.getFrom());
        telegramBot.sendMessage(msg.getFrom().getId(), "Hi " + msg.getFrom().getUserName());
        utils.getOperation().setLastCommand("/start");
        MappingUtils.getSTASH().put(msg.getFrom().getId(), utils.getOperation());
        telegramBot.sendMessage(sendInlineKeyBoardMessage(user.getId()));
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.START.name);
    }

    @Override
    public SendMessage sendInlineKeyBoardMessage(long chatId) {
            telegramBot.inlineKeyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
            List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
            inlineKeyboardButton1.setText(EmojiParser.parseToUnicode("Расходы :money_face:"));
            inlineKeyboardButton1.setCallbackData("expansesButton");
            inlineKeyboardButton2.setText(EmojiParser.parseToUnicode("Доходы :moneybag:"));
            inlineKeyboardButton2.setCallbackData("incomesButton");
            keyboardButtonsRow1.add(inlineKeyboardButton1);
            keyboardButtonsRow2.add(inlineKeyboardButton2);
            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            rowList.add(keyboardButtonsRow1);
            rowList.add(keyboardButtonsRow2);
            telegramBot.inlineKeyboardMarkup.setKeyboard(rowList);
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Выберите дальнейшее действие");
            message.setReplyMarkup(telegramBot.inlineKeyboardMarkup);
            return message;
    }
}

