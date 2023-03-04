package ru.telegram.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telegram.entity.ExpansesEntity;

@Repository
public interface ExpansesEntityRepository extends JpaRepository <ExpansesEntity, Integer> {
}
