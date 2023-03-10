package ru.telegram.service.handler.imp;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.controller.Bot;
import ru.telegram.service.handler.CommandEnum;
import ru.telegram.service.handler.CommandHandler;

@Component
public class HelpHandler implements CommandHandler {
    @Lazy
    @Autowired
    private Bot bot;

    @Override
    public void handle(Message msg) {
        User user = msg.getFrom();
        bot.sendText(user.getId(), EmojiParser.parseToUnicode("Бог поможет! :ru:"));
    }

    @Override
    public boolean isMatch(String msgText) {
        return msgText.equals(CommandEnum.HELP.name);
    }
}
