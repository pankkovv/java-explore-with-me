package ru.practicum.main.model;

import ru.practicum.main.dto.EventFullDto;

import java.util.List;

public class Compilation {
    private int id;
    private List<EventFullDto> events;
    private boolean pinned;
    private String title;
}
