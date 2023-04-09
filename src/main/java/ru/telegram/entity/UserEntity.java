package ru.telegram.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user", schema = "bot")
public class UserEntity {

    @Id
    @Column(name = "chat_id", nullable = false)
    Long chatId;
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
     * Started at
     */
    @Column(name = "started_at")
    LocalDateTime startedAt;

    /**
     * Balance
     */
    @Column(name = "balance")
    Double balance;

    /**
     * Last command
     */
    @Column(name = "last_command")
    String lastCommand;

    /**
     * Expanses
     */
    @OneToMany
    @JoinColumn(name = "chat_id")
    List<ExpansesEntity> expanses;

    /**
     * Incomes
     */
    @OneToMany
    @JoinColumn(name = "chat_id")
    List<IncomesEntity> incomes;

}
