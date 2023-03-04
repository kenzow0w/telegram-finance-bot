package ru.telegram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.service.ExpensesServiceImpl;


@Controller
public class ExpansesController {

    ExpensesServiceImpl expensesService;

    private Update update;

    @RequestMapping("Доход")
    public SendMessage getAllExpanses(){
        if (!update.getMessage().hasText()) {
            return null;
        }
        long chatId = update.getMessage().getChatId();
        String name = update.getMessage().getChat().getFirstName() + " " + update.getMessage().getChat().getLastName();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("На ваш счет зачислено");
        return sendMessage;
    }



}
