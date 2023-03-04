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
@Table(name = "difference", schema = "public")
public class DifferenceEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Ammount
     */
    @Column(name = "ammount")
    Integer ammount;

}
