package ru.telegram.utils;

public enum Command {
    ADD_EXPANSES("/add_expanses", "Add expanses"),
    ADD_INCOME("/add_income", "Add income"),
    SHOW_EXPANSES("/show_expanses", "Show expanses"),
    SHOW_INCOME("/show_income", "Show income"),
    BALANCE("/balance", "Balance");

    private final String name;
    private final String description;

    Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
