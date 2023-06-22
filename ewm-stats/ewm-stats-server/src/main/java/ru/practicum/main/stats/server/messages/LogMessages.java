package ru.practicum.main.stats.server.messages;

public enum LogMessages {
    TRY_POST_HIT("Попытка добавить запись в статистику."),
    TRY_GET_STATS("Попытка получить записи статистики."),
    POST_HIT("Записи статистики успешно добавлены."),
    GET_STATS("Записи статистики успешно получены.");


    public final String label;

    LogMessages(String label) {
        this.label = label;
    }
}
