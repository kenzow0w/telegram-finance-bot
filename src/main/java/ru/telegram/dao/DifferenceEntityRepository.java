package ru.telegram.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.telegram.entity.DifferenceEntity;

@Repository
public interface DifferenceEntityRepository extends CrudRepository<DifferenceEntity, Integer> {
}
