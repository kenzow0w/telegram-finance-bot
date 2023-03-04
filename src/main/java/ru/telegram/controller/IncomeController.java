package ru.telegram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.service.IncomeServiceImpl;

@Controller
public class IncomeController {

    IncomeServiceImpl incomeService;

    @RequestMapping("Add income")
    public SendMessage addIncome(){

        return null;
    }

}
