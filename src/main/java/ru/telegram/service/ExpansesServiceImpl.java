package ru.telegram.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
        String selectSql = " select * from bot.expanses " +
                " where chat_id = " + id +
                " order by created_at desc limit 1 ";
        return namedParameterJdbcTemplate.query(selectSql, new BeanPropertyRowMapper<>(ExpansesEntity.class)).get(0);
    }

    public List<ExpansesEntity> findAllForLastMonth(long id) {
        List<ExpansesEntity> expanses = null;
        try {
            String selectSql = " select * from bot.expanses " +
                    " where created_at > date_trunc('month', current_date) and chat_id = " + id;
            expanses = namedParameterJdbcTemplate.query(selectSql, new BeanPropertyRowMapper<>(ExpansesEntity.class));
        } catch (RuntimeException e) {
            System.out.println("За текущий месяц затраты равны 0");
        }
        return expanses;
    }

    public List<ExpansesEntity> findAllForYear(long id) {
        String selectSql = """
                select * from bot.expanses
                where created_at > date_trunc('year', current_date) and chat id = ;
                """ + id;
        return namedParameterJdbcTemplate.query(selectSql, new BeanPropertyRowMapper<>(ExpansesEntity.class));
    }
}
