package ru.telegram.service;


import org.springframework.stereotype.Service;
import ru.telegram.dao.ExpansesEntityRepository;
import ru.telegram.dao.IncomeEntityRepository;
import ru.telegram.dao.UserEntityRepository;

@Service
public class ExpansesServiceImpl {

    private ExpansesEntityRepository expansesEntityRepository;

    public ExpansesServiceImpl(UserEntityRepository userEntityRepository, IncomeEntityRepository incomeEntityRepository, ExpansesEntityRepository expensesEntityRepository){
        this.expansesEntityRepository = expensesEntityRepository;
    }
}
