package ru.practicum.main.exception.controller;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidTimeException;
import ru.practicum.main.exception.model.ApiError;
import ru.practicum.main.exception.model.ExceptionStatus;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        ApiError error = new ApiError();
        error.setErrors(List.of(e.getStackTrace().toString()));
        error.setMessage(error.getMessage());
        error.setReason("Искомый объект не найден.");
        error.setStatus(ExceptionStatus.NOT_FOUND.toString());
        error.setTimestamp(LocalDateTime.now());
        return error;
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidTimeException(final ValidTimeException e) {
        ApiError error = new ApiError();
        error.setErrors(List.of(e.getStackTrace().toString()));
        error.setMessage(error.getMessage());
        error.setReason("Запрос составлен некорректно.");
        error.setStatus(ExceptionStatus.NOT_FOUND.toString());
        error.setTimestamp(LocalDateTime.now());
        return error;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final ConflictException e) {
        ApiError error = new ApiError();
        error.setErrors(List.of(e.getStackTrace().toString()));
        error.setMessage(error.getMessage());
        error.setReason("Нарушение целостности данных.");
        error.setStatus(ExceptionStatus.NOT_FOUND.toString());
        error.setTimestamp(LocalDateTime.now());
        return error;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final SQLException e) {
        ApiError error = new ApiError();
        error.setErrors(List.of(e.getStackTrace().toString()));
        error.setMessage("Нарушение ограничения SQL.");
        error.setReason("Нарушение целостности данных.");
        error.setStatus(ExceptionStatus.NOT_FOUND.toString());
        error.setTimestamp(LocalDateTime.now());
        return error;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConflictException(final MethodArgumentNotValidException e) {
        ApiError error = new ApiError();
        error.setErrors(List.of(e.getStackTrace().toString()));
        error.setMessage("");
        error.setReason("Запрос составлен некорректно.");
        error.setStatus(ExceptionStatus.NOT_FOUND.toString());
        error.setTimestamp(LocalDateTime.now());
        return error;
    }

}