package ru.practicum.main.requests.close.service;

import ru.practicum.main.requests.dto.ParticipationRequestDto;

import java.util.List;

public interface CloseRequestsService {
    List<ParticipationRequestDto> getRequestsByUserOtherEvents(int userId);

    ParticipationRequestDto createRequestsByUserOtherEvents(int userId, int eventId);

    ParticipationRequestDto cancelRequestsByUserOtherEvents(int userId, int requestId);
}
