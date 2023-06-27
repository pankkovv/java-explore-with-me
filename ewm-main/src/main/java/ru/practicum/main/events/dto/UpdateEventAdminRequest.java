package ru.practicum.main.events.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.main.events.model.StateActionAdmin;
import ru.practicum.main.locations.model.Location;

@Data
@Builder
public class UpdateEventAdminRequest {
    private String annotation;
    private Integer category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionAdmin stateAction;
    private String title;
}
