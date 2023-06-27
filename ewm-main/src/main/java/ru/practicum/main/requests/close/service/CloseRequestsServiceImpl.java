package ru.practicum.main.requests.close.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.events.close.service.CloseEventsService;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.requests.dto.ParticipationRequestDto;
import ru.practicum.main.requests.model.ParticipationRequest;
import ru.practicum.main.requests.model.StatusEventRequestUpdateResult;
import ru.practicum.main.requests.repository.RequestsRepository;
import ru.practicum.main.users.admin.service.AdminUsersService;
import ru.practicum.main.users.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.requests.mapper.RequestsMap.*;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class CloseRequestsServiceImpl implements CloseRequestsService {
    @Autowired
    private RequestsRepository repository;
    @Autowired
    private AdminUsersService usersService;
    @Autowired
    private CloseEventsService eventsService;

    @Override
    public List<ParticipationRequestDto> getRequestsByUserOtherEvents(int userId) {
        return mapToListParticipationRequestDto(repository.findParticipationRequestsByRequester_Id(userId));
    }

    @Override
    public ParticipationRequestDto createRequestsByUserOtherEvents(int userId, int eventId) {
        User user = usersService.getUserById(userId);
        Event event = eventsService.getEventById(eventId);

        if (event.getInitiator() != user) {
            ParticipationRequest request = ParticipationRequest.builder()
                    .created(LocalDateTime.now())
                    .event(event)
                    .requester(user)
                    .status(StatusEventRequestUpdateResult.PENDING)
                    .build();
            return mapToParticipationRequestDto(repository.save(request));
        } else {
            throw new RuntimeException();
        }

    }

    @Override
    public ParticipationRequestDto cancelRequestsByUserOtherEvents(int userId, int requestId) {
        ParticipationRequest request = repository.findParticipationRequestByIdAndRequester_Id(requestId, userId);
        request.setStatus(StatusEventRequestUpdateResult.CANCELED);
        return mapToParticipationRequestDto(repository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(int userId, int eventId) {
        return mapToListParticipationRequestDto(repository.findParticipationRequestsByRequester_IdAndEvent_Id(userId, eventId));
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusRequestsByUser(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        List<ParticipationRequest> requestList = repository.findParticipationRequestsByRequester_Id(userId);

        for (ParticipationRequest request : requestList) {
            if (request.getEvent().getId() == eventId) {
                request.setStatus(eventRequestStatusUpdateRequest.getStatus());
            }
        }

        return mapToEventRequestStatusUpdateResult(requestList);
    }
}
