package ru.telegram.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.telegram.service.ExpansesServiceImpl;


@Controller
@AllArgsConstructor
public class ExpansesController {

    private ExpansesServiceImpl expansesService;

//    public void saveCategory(long id, String category) {
//        expansesService.saveCategory(id, category);
//    }
}
