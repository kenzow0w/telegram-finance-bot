package ru.telegram.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.telegram.service.IncomeServiceImpl;

@Component
public class IncomeController {

    public IncomeController(IncomeServiceImpl incomeService) {
        this.incomeService = incomeService;
    }

    private IncomeServiceImpl incomeService;

    public Integer balance(long id) {
        return incomeService.balance(id);
    }

    public String add(long id, int amount) {
        return incomeService.add(id, amount);
    }
}
