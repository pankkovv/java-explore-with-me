package ru.practicum.main.compilations.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.main.events.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CompilationDto {
    private List<EventShortDto> events;
    @NotNull
    private int id;
    @NotNull
    private boolean pinned;
    @NotBlank
    private String title;
}
