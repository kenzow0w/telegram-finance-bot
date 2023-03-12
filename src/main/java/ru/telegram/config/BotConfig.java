package ru.telegram.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class BotConfig {

    @Value("${bot.name}")
    private String BOT_NAME;

    @Value("${bot.token}")
    private String BOT_TOKEN;
}
