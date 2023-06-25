package ru.practicum.main.events.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.events.enums.EventStatus;
import ru.practicum.main.locat.model.Location;

@Data
@Builder
public class UpdateEventAdminRequest {
    private String annotation;
    private CategoryDto category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private EventStatus stateAction;
    private String title;
}
