package ru.practicum.main.categories.exception;

public class NotFoundCategories extends RuntimeException{
    public NotFoundCategories(String message){
        super(message);
    }
}
