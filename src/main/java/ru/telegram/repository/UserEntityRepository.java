package ru.telegram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telegram.entity.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}
