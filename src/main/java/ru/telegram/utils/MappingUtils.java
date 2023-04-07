package ru.telegram.utils;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.entity.ExpensesEntity;
import ru.telegram.entity.IncomeEntity;
import ru.telegram.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MappingUtils {

    public static User mapToUser(UserEntity userEntity){
        User user = new User();
        user.setId(userEntity.getChatId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setUserName(userEntity.getUserName());
        return user;
    }

    public static UserEntity mapToUserEntity(User user){
        try {
            UserEntity entity = new UserEntity();
            entity.setChatId(user.getId());
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setUserName(user.getUserName());
            entity.setStartedAt(LocalDateTime.now());
            entity.setBalance(0.0);
            List<ExpensesEntity> expensesEntity = entity.getExpenses();
            if(expensesEntity != null){
                entity.setExpenses(expensesEntity);
            }
            List<IncomeEntity> incomeEntity = entity.getIncomes();
            if(incomeEntity != null){
                entity.setIncomes(incomeEntity);
            }
            return entity;
        }catch (RuntimeException e){
            return null;
//            throw new RuntimeException("UserEntity doesn't exist in db");
        }
    }


}
