package ru.practicum.main.events.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.main.locations.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class NewEventDto {
    @NotBlank
    @Length(min = 19, max = 2001)
    private String annotation;
    @NotNull
    private int category;
    @NotBlank
    @Length(min = 19, max = 7001)
    private String description;
    @NotBlank
    private String eventDate;
    @NotNull
    private Location location;
    @Builder.Default
    private boolean paid = false;
    @Builder.Default
    private int participantLimit = 0;
    @Builder.Default
    private boolean requestModeration = true;
    @NotBlank
    @Length(min = 2, max = 121)
    private String title;
}
