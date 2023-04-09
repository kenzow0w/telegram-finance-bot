package ru.telegram.utils;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.entity.ExpansesEntity;
import ru.telegram.entity.IncomesEntity;
import ru.telegram.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MappingUtils {

    public static Map<Long, Operation> STASH = new HashMap<>();

    public static Map<Long, Operation> getSTASH(){
        return STASH;
    }

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
            List<ExpansesEntity> expansesEntity = entity.getExpanses();
            if(expansesEntity != null){
                entity.setExpanses(expansesEntity);
            }
            List<IncomesEntity> incomesEntity = entity.getIncomes();
            if(incomesEntity != null){
                entity.setIncomes(incomesEntity);
            }
            return entity;
        }catch (RuntimeException e){
            return null;
//            throw new RuntimeException("UserEntity doesn't exist in db");
        }
    }


}
