package ru.telegram.controller;

import org.springframework.stereotype.Controller;
import ru.telegram.service.IncomeServiceImpl;
import ru.telegram.utils.Operation;

@Controller
public class IncomeController {

    private IncomeServiceImpl incomeService;

    public void save(long id, Operation operation) {
        incomeService.save(id, operation);
    }
}
