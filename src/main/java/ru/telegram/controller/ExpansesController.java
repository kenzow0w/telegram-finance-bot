package ru.telegram.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.telegram.entity.ExpansesEntity;
import ru.telegram.service.ExpansesServiceImpl;
import ru.telegram.utils.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@AllArgsConstructor
public class ExpansesController {

    private ExpansesServiceImpl expansesService;

    public void save(long id, Operation operation) {
        expansesService.save(id, operation);
    }

    public void getLast(long id) {
        expansesService.getLast(id);
    }

    public void findAllForLastMonth(long id) {
        Map<String, Double> ex = new HashMap<>();
        List<ExpansesEntity> expanses = null;
        try {
            expanses = expansesService.findAllForLastMonth(id);
            for (ExpansesEntity entity : expanses) {
                if (ex.containsKey(entity.getCategory())) {
                    ex.put(entity.getCategory(), ex.get(entity.getCategory()) + entity.getAmount());
                } else {
                    ex.put(entity.getCategory(), entity.getAmount());
                }
            }
            for (Map.Entry entry : ex.entrySet()) {
                System.out.println("[Key] : " + entry.getKey() + " [Value] : " + entry.getValue());
            }
        } catch (RuntimeException e) {
            System.out.println("За текущий месяц затраты равны 0");
        }
    }

    public void findAllForYear(long id) {
        expansesService.findAllForYear(id);
    }
}
