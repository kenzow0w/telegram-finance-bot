package ru.telegram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.dao.ExpansesEntityRepository;
import ru.telegram.dao.IncomeEntityRepository;
import ru.telegram.dao.UserEntityRepository;
import ru.telegram.entity.UserEntity;

import static ru.telegram.utils.MappingUtils.mapToUserEntity;

@Service
@Transactional
public class UserServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserEntityRepository userEntityRepository;

    public UserServiceImpl(UserEntityRepository userEntityRepository, IncomeEntityRepository incomeEntityRepository, ExpansesEntityRepository expensesEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }

    public UserEntity getOne(long id) {
        try{
            return userEntityRepository.findAll().stream().filter(u -> u.getChatId() == id).findFirst().get();
        }catch (RuntimeException e){
            return null;
        }
    }

    public User save(User user) {
        UserEntity userEntity = mapToUserEntity(user);
        LOG.info("User added: " + (userEntity != null ? userEntity.getUserName() : userEntity.getFirstName()));
        userEntityRepository.save(userEntity);
        return user;
    }

    public void saveLastCommand(long id, String lastCommand) {
        UserEntity userEntity = getOne(id);
        userEntity.setLastCommand(lastCommand);
        userEntityRepository.save(userEntity);
    }
}
