package ru.practicum.main.events.open.service;

import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.EventShortDto;

import java.util.List;

public class OpenEventsServiceImpl implements OpenEventsService {
    @Override
    public List<EventShortDto> getEvents(String text, List<Integer> categories, boolean paid, String rangeStart, String rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getEventsById(int eventId) {
        return null;
    }
}
