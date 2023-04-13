package ru.telegram.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.dao.IncomeEntityRepository;
import ru.telegram.entity.ExpansesEntity;
import ru.telegram.entity.IncomesEntity;
import ru.telegram.entity.UserEntity;
import ru.telegram.utils.Operation;

import java.time.LocalDateTime;


@Service
@Transactional
public class IncomeServiceImpl {

    private final IncomeEntityRepository incomeEntityRepository;

    private UserServiceImpl userService;

    public IncomeServiceImpl(IncomeEntityRepository incomeEntityRepository, UserServiceImpl userService) {
        this.incomeEntityRepository = incomeEntityRepository;
        this.userService = userService;
    }


    public void save(long id, Operation operation) {
        IncomesEntity incomesEntity = new IncomesEntity();
        UserEntity userEntity = userService.getOne(id);
        incomesEntity.setUserEntity(userEntity);
        incomesEntity.setLocalDateTime(LocalDateTime.now());
        incomesEntity.setAmount(operation.getAmount());
        incomesEntity.setCategory(operation.getCategory());
        incomeEntityRepository.save(incomesEntity);

        double value = userEntity.getBalance() + operation.getAmount();
        userEntity.setBalance(value);
        userService.save(userEntity);
    }
}
