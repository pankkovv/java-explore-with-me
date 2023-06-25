package ru.practicum.main.events.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.events.enums.EventStatus;
import ru.practicum.main.locat.model.Location;

@Data
@Builder
public class UpdateEventUserRequest {
    @Length(min = 20, max = 2000)
    private String annotation;
    private CategoryDto category;
    @Length(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private EventStatus stateAction;
    @Length(min = 3, max = 120)
    private String title;
}
