package ru.telegram.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Operation {
    String lastCommand;
    String category;
    Double amount;
}
