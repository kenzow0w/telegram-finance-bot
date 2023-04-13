package ru.telegram.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "incomes", schema = "bot")
public class IncomesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private UserEntity userEntity;

    /**
     * CreatedAt
     */
    @Column(name="created_at", nullable = false)
    LocalDateTime localDateTime;
    
    /**
     * Category
     */
    @Column(name = "category")
    String category;

    /**
     * Amount
     */
    @Column(name = "amount")
    Double amount;
}
