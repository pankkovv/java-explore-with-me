package ru.practicum.main.exception;

public class NotFoundCategories extends RuntimeException {
    public NotFoundCategories(String message) {
        super(message);
    }
}
