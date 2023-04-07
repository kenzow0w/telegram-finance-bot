package ru.telegram.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public enum InlineEnum {
    EXPANSES("Расходы"),
    INCOMES("Доходы"),
    BACK("Назад");

    String name;
}
