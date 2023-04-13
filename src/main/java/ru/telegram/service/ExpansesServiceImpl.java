package ru.telegram.service;


import org.springframework.stereotype.Service;
import ru.telegram.dao.ExpansesEntityRepository;
import ru.telegram.dao.IncomeEntityRepository;
import ru.telegram.dao.UserEntityRepository;
import ru.telegram.entity.ExpansesEntity;
import ru.telegram.entity.UserEntity;
import ru.telegram.utils.Operation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpansesServiceImpl {

    private ExpansesEntityRepository expansesEntityRepository;

    private UserServiceImpl userService;

    public ExpansesServiceImpl(UserServiceImpl userService, ExpansesEntityRepository expensesEntityRepository) {
        this.expansesEntityRepository = expensesEntityRepository;
        this.userService = userService;
    }

    public void save(long id, Operation operation) {
        ExpansesEntity expansesEntity = new ExpansesEntity();
        UserEntity userEntity = userService.getOne(id);
        expansesEntity.setUserEntity(userEntity);
        expansesEntity.setLocalDateTime(LocalDateTime.now());
        expansesEntity.setAmount(operation.getAmount());
        expansesEntity.setCategory(operation.getCategory());
        expansesEntityRepository.save(expansesEntity);

        double value = userEntity.getBalance() - operation.getAmount();
        userEntity.setBalance(value);
        userService.save(userEntity);
    }

    public ExpansesEntity getLast(long id) {
        UserEntity userEntity = userService.getOne(id);
        return expansesEntityRepository.findAll().stream().filter(ex -> id == userEntity.getChatId()).toList().get(-1);
    }

    public List<ExpansesEntity> findAllForLastMonth(long id) {
        UserEntity userEntity = userService.getOne(id);
        return expansesEntityRepository.findAll().stream().filter(ex -> id == userEntity.getChatId()).filter(ex -> ex.getLocalDateTime().isAfter(LocalDateTime.now().minusMonths(1))).collect(Collectors.toList());
    }
}
