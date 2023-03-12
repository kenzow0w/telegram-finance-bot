package ru.telegram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.dao.ExpensesEntityRepository;
import ru.telegram.dao.IncomeEntityRepository;
import ru.telegram.dao.UserEntityRepository;
import ru.telegram.entity.UserEntity;

import static ru.telegram.utils.MappingUtils.mapToUser;
import static ru.telegram.utils.MappingUtils.mapToUserEntity;

@Service
@Transactional
public class UserServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserEntityRepository userEntityRepository;

    public UserServiceImpl(UserEntityRepository userEntityRepository, IncomeEntityRepository incomeEntityRepository, ExpensesEntityRepository expensesEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }

    public User getOne(long id) {
        try{
            return mapToUser(userEntityRepository.findAll().stream().filter(u -> u.getChatId() == id).findFirst().get());
        }catch (RuntimeException e){
            return null;
        }

    }

    public User save(User user) {
        UserEntity userEntity = mapToUserEntity(user);
        LOG.info("User added: " + (userEntity != null ? userEntity.getUserName() : userEntity.getFirstName()));
        return user;
    }
}
