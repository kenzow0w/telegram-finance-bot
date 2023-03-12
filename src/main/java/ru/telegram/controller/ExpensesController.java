package ru.telegram.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.telegram.service.ExpensesServiceImpl;


@Controller
@AllArgsConstructor
public class ExpensesController {

    private ExpensesServiceImpl expensesService;

}
