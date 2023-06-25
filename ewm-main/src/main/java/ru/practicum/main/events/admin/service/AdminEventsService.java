package ru.practicum.main.events.admin.service;

import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.UpdateEventAdminRequest;

import java.util.List;

public interface AdminEventsService {
    List<EventFullDto> findEvents(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart, String rangeEnd, int from, int size);

    EventFullDto changeEvents(int eventId, UpdateEventAdminRequest updateEventAdminRequest);
}
