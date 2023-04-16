package ru.telegram.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.telegram.service.IncomeServiceImpl;
import ru.telegram.utils.Operation;

@Controller
@AllArgsConstructor
public class IncomeController {

    private IncomeServiceImpl incomeService;

    public void save(long id, Operation operation) {
        incomeService.save(id, operation);
    }
}
