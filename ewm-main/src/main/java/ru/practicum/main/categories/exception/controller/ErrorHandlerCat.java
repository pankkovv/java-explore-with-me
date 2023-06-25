package ru.practicum.main.categories.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.categories.exception.NotFoundCategories;
import ru.practicum.main.users.exception.model.ApiError;


@RestControllerAdvice
public class ErrorHandlerCat {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundCategories e) {
        return new ApiError();
    }

}