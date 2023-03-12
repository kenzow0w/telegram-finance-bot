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
     * Categories
     */
    @Column(name = "categories")
    String categories;

    /**
     * Categories
     */
    @Column(name = "amount", nullable = false)
    Integer amount;

}