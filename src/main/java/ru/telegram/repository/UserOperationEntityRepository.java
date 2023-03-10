package ru.telegram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.telegram.entity.UserOperationEntity;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserOperationEntityRepository extends JpaRepository<UserOperationEntity, Long> {
    List<UserOperationEntity> findAllByUserId(Long userId);

    @Query("""
            SELECT COALESCE(SUM(uoe.sum), 0)
            FROM userOperationEntity uoe
            WHERE uoe.user.id = :userId
            """)
    BigDecimal getTotalSum(Long userId);

    @Query("""
            SELECT COALESCE(SUM(uoe.sum), 0)
            FROM userOperationEntity uoe
            WHERE uoe.user.id = :userId
            AND uoe.sum > 0
            """)
    BigDecimal getInSum(Long userId);

    @Query("""
            SELECT COALESCE(SUM(uoe.sum), 0)
            FROM userOperationEntity uoe
            WHERE uoe.user.id = :userId
            AND uoe.sum < 0
            """)
    BigDecimal getOutSum(Long userId);

    @Transactional
    @Modifying
    @Query("""
            DELETE FROM userOperationEntity uoe
            WHERE uoe.user.id = :userId
            AND uoe.id = (SELECT MAX(userOpEn.id)
            FROM userOperationEntity userOpEn
            WHERE userOpEn.user.id = :userId)
            """)
    void deleteLast(Long userId);
}
