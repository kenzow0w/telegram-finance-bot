package ru.telegram.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.telegram.entity.ExpensesEntity;

@Repository
public interface ExpensesEntityRepository extends JpaRepository <ExpensesEntity, Integer> {
}
