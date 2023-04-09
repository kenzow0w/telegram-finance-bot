package ru.telegram.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.telegram.service.ExpansesServiceImpl;
import ru.telegram.utils.Operation;


@Controller
@AllArgsConstructor
public class ExpansesController {

    private ExpansesServiceImpl expansesService;

    public void save(long id, Operation operation) {
        expansesService.save(id, operation);
    }
}
