package ru.practicum.main.model;

import ru.practicum.main.dto.CategoryDto;
import ru.practicum.main.dto.Location;
import ru.practicum.main.dto.UserShortDto;

public class Event {
    private int id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private String title;
    private int views;
}
