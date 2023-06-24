package ru.practicum.main.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CompilationDto {
    private List<EventFullDto> events;
    @NotNull
    private int id;
    @NotNull
    private boolean pinned;
    @NotBlank
    private String title;
}
