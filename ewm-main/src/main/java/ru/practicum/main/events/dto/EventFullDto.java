package ru.practicum.main.events.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.compilations.model.Compilations;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.locations.model.Location;
import ru.practicum.main.users.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class EventFullDto {
    private int id;
    @NotBlank
    private String annotation;
    @NotNull
    private CategoryDto category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    @NotBlank
    private String eventDate;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Location location;
    @NotNull
    private boolean paid;
    @Builder.Default
    private int participantLimit = 0;
    private String publishedOn;
    @Builder.Default
    private boolean requestModeration = true;
    private EventStatus state;
    @NotBlank
    private String title;
    private int views;
}
