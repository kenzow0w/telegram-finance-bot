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
@Table(name = "income", schema = "public")
public class IncomeEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Category
     */
    @Column(name = "categories")
    String categories;

    /**
     * Amount
     */
    @Column(name = "ammount")
    Integer ammount;


}
