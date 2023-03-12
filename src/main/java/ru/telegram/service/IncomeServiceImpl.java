package ru.telegram.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.dao.IncomeEntityRepository;
import ru.telegram.entity.IncomeEntity;


@Service
@Transactional
public class IncomeServiceImpl {

    private final IncomeEntityRepository incomeEntityRepository;

    public IncomeServiceImpl(IncomeEntityRepository incomeEntityRepository) {
        this.incomeEntityRepository = incomeEntityRepository;
    }

    public static SendMessage save(String value) {
//        return incomeEntityRepository.save()
        return null;
    }

    public Integer balance(long chatId) {
        return incomeEntityRepository.findAll().stream().filter(i -> i.getId() == chatId).findFirst().get().getAmount();
    }

    public String add(long id, int amount) {
        IncomeEntity incomeEntity = incomeEntityRepository.findAll().stream().filter(i -> i.getId() == id).findFirst().get();
        int currentValue = incomeEntity.getAmount();
        incomeEntity.setAmount(currentValue + amount);
        incomeEntityRepository.save(incomeEntity);
        return String.valueOf(incomeEntity.getAmount());
    }
}
