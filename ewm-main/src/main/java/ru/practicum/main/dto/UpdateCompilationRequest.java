package ru.practicum.main.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public class UpdateCompilationRequest {
    @UniqueElements
    private List<Integer> events;
    private boolean pinned;
    @Length(min = 1, max = 50)
    private String title;
}
