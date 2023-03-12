package ru.telegram.utils;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.entity.UserEntity;

@Service
public class MappingUtils {

    public static User mapToUser(UserEntity userEntity){
        User user = new User();
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        return user;
    }

    public static UserEntity mapToUserEntity(User user){
        try {
            UserEntity entity = new UserEntity();
            entity.setChatId(user.getId());
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setUserName(user.getUserName());
//            ExpensesEntity expensesEntity = entity.getExpenses();
//            if(expensesEntity != null){
//                entity.setExpenses()
//            }
//            IncomeEntity incomeEntity = entity.getIncome();
//            if(incomeEntity != null){
//                entity.setIncome(incomeEntity);
//            }
            return entity;
        }catch (RuntimeException e){
            return null;
//            throw new RuntimeException("UserEntity doesn't exist in db");
        }
    }


}
