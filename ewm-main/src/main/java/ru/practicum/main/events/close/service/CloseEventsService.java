package ru.practicum.main.events.close.service;

import ru.practicum.main.events.dto.*;
import ru.practicum.main.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface CloseEventsService {
    List<EventShortDto> getEventsByUser(int userId, int from, int size);

    EventFullDto createEvents(int userId, NewEventDto newEventDto);

    EventFullDto getEventsByUserFullInfo(int userId, int eventId);

    EventFullDto changeEventsByUser(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getRequestsByUser(int userId, int eventId);

    EventRequestStatusUpdateResult changeStatusRequestsByUser(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
