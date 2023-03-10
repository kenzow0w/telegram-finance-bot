package ru.telegram.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "userOperationEntity")
public class UserOperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    BigDecimal sum;
    String category;
    LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    UserEntity user;
}
