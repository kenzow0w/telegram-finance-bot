package ru.telegram.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telegram.entity.IncomesEntity;

@Repository
public interface IncomeEntityRepository extends JpaRepository <IncomesEntity, Integer> {

}
