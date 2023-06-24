package ru.practicum.main.dto;

import lombok.Builder;
import lombok.Data;

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
    private String stateAction;
    private String title;
}
