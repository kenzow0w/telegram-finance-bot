package ru.telegram.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public enum CommandEnum {
    START("/start"),
    INFO("/info"),
    DELETE("/clear"),
    HISTORY("/history"),
    HELP("/help");

    String name;

}
