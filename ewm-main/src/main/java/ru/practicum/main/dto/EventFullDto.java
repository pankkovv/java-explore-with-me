package ru.practicum.main.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class EventFullDto {
    @NotBlank
    private String annotation;
    @NotNull
    private CategoryDto category;
    private int confirmedRequests;
    private String createdOd;
    private String description;
    @NotBlank
    private String eventDate;
    private int id;
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
    private String state;
    @NotBlank
    private String title;
    private int views;
}
