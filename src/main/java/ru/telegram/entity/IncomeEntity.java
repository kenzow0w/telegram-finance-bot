package ru.telegram.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "incomes", schema = "bot")
public class IncomeEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * CreatedAt
     */
    @Column(name="createdAt", nullable = false)
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
    Integer amount;


}
