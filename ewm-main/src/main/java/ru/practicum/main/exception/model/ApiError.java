package ru.practicum.main.exception.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private String status;
    private LocalDateTime timestamp;
}