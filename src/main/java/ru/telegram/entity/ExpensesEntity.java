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
@Table(name = "expanses", schema = "bot")
public class ExpensesEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * CreatedAt
     */
    @Column(name="createdAt", nullable = false)
    LocalDateTime localDateTime;

    /**
     * Categories
     */
    @Column(name = "category")
    String category;

    /**
     * Categories
     */
    @Column(name = "amount", nullable = false)
    Integer amount;

}
