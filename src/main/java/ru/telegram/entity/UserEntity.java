package ru.telegram.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user", schema = "bot")
public class UserEntity {

    @Id
    @Column(name = "chat_id", nullable = false)
    long chatId;
    /**
     * First name
     */
    @Column(name = "first_name")
    String firstName;
    /**
     * Last name
     */
    @Column(name = "last_name")
    String lastName;

    /**
     * User name
     */
    @Column(name = "user_name")
    String userName;

    /**
     * Expenses
     */
    @ManyToOne
    @JoinColumn(name = "expenses_id")
    ExpensesEntity expenses;

    /**
     * Income
     */
    @ManyToOne
    @JoinColumn(name = "income_id")
    IncomeEntity income;

}
