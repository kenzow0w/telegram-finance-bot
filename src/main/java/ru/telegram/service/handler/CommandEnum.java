package ru.telegram.service.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public enum CommandEnum {
    START("/start"),
    INFO("/info"),
    OPERATION("/operation"),
    DELETE("/delete"),
    HISTORY("/history"),
    HELP("/help");
    String name;
}
