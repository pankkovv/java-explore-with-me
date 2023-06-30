package ru.practicum.main.messages;

public enum ExceptionMessages {
    NOT_FOUND_EXCEPTION("Объект не найден или недоступен."),
    CONFLICT_EXCEPTION("Нарушение целостности данных."),
    VALID_TIME_EXCEPTION("Данные не удовлетворяет правилам создания.");

    public final String label;

    ExceptionMessages(String label) {
        this.label = label;
    }
}
