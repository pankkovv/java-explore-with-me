package ru.practicum.main.dto;

import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class NewCompilationDto {
    @UniqueElements
    private List<Integer> events;
    @Builder.Default
    private boolean pinned = false;
    @NotBlank
    @Length(min = 1, max = 50)
    private String title;
}
