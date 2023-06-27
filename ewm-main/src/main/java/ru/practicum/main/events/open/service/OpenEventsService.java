package ru.practicum.main.events.open.service;

import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.OpenEventRequests;
import ru.practicum.main.events.dto.EventShortDto;

import java.util.List;

public interface OpenEventsService {
    List<EventShortDto> getEvents(OpenEventRequests requests);

    EventFullDto getEventsById(int eventId);
}
