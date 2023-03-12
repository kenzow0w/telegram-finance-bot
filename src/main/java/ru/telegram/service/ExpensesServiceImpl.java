package ru.telegram.service;


import org.springframework.stereotype.Service;
import ru.telegram.dao.ExpensesEntityRepository;
import ru.telegram.entity.ExpensesEntity;

@Service
public class ExpensesServiceImpl {

    private ExpensesEntityRepository expensesEntityRepository;

    public int getAmount(Long userId) {
        ExpensesEntity expenses = expensesEntityRepository.findAll().stream().filter(i -> i.getId() == userId).findFirst().get();
        return expenses.getAmount();
    }
}
