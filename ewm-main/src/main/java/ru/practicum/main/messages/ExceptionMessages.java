package ru.practicum.main.messages;

public enum ExceptionMessages {
    NOT_FOUND_EXCEPTION("Объект не найден или недоступен."),
    NOT_FOUND_CATEGORIES_EXCEPTION("Категория не найдена или недоступна."),
    NOT_FOUND_COMPILATIONS_EXCEPTION("Подборка не найдена или недоступна."),
    NOT_FOUND_EVENTS_EXCEPTION("Событие не найдено или недоступно."),
    NOT_FOUND_REQUESTS_EXCEPTION("Запрос не найден или недоступен."),
    NOT_FOUND_USERS_EXCEPTION("Пользователь не найден или недоступен."),
    CONFLICT_EXCEPTION("Нарушение целостности данных."),
    VALID_TIME_EXCEPTION("Данные не удовлетворяет правилам создания."),
    NOT_SUBSCRIBE_YOURSELF_EXCEPTION("Нельзя подписаться на себя."),
    NOT_UNSUBSCRIBE_YOURSELF_EXCEPTION("Нельзя отписаться от себя."),
    NOT_SUBSCRIPTIONS_UNRESOLVED_EXCEPTION("Данный пользователь запретил подписываться на него."),
    NOT_SUBSCRIPTIONS_ALREADY_EXCEPTION("Подписка на данного пользователя уже оформлена."),
    NOT_SUBSCRIPTIONS_EXCEPTION("Подписка на данного пользователя не оформлена."),
    NOT_SUBSCRIPTIONS_EMPTY_EXCEPTION("Нет оформленных подписок."),
    NOT_SUBSCRIBER_EXCEPTION("Пользователь не подписан на вас."),
    NOT_SUBSCRIBER_EMPTY_EXCEPTION("Нет подписчиков.");


    public final String label;

    ExceptionMessages(String label) {
        this.label = label;
    }
}
