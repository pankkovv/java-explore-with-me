package ru.practicum.main.requests.close.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.events.close.service.CloseEventsService;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;
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
        usersService.getUserById(userId);
        return mapToListParticipationRequestDto(repository.findParticipationRequestsByRequester_Id(userId));
    }

    @Override
    public ParticipationRequestDto createRequestsByUserOtherEvents(int userId, int eventId) {
        User user = usersService.getUserById(userId);
        Event event = eventsService.getEventById(eventId);
        List<ParticipationRequestDto> requestDtoList = getRequestsByUserOtherEvents(userId);

        boolean conditionOne = false;

        if (requestDtoList != null && !requestDtoList.isEmpty()) {
            for (ParticipationRequestDto requestDto : requestDtoList) {
                if (requestDto.getEvent() == eventId) {
                    conditionOne = true;
                    break;
                }
            }
        }

        boolean conditionTwo = event.getInitiator().getId() == user.getId();
        boolean conditionThree = event.getState().equals(EventStatus.PENDING) || event.getState().equals(EventStatus.CANCELED);
        boolean conditionFour = event.getConfirmedRequests() >= event.getParticipantLimit();
        boolean conditionFive = !event.isRequestModeration();

        if (conditionOne || conditionTwo || conditionThree || conditionFour) {
            throw new ConflictException("Нарушение целостности данных.");
        }

        ParticipationRequest request = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .eventsWithRequests(event)
                .requester(user)
                .build();

        if (conditionFive) {
            request.setStatus(StatusEventRequestUpdateResult.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        } else {
            request.setStatus(StatusEventRequestUpdateResult.PENDING);
        }

        return mapToParticipationRequestDto(repository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelRequestsByUserOtherEvents(int userId, int requestId) {
        ParticipationRequest request = repository.findParticipationRequestByIdAndRequester_Id(requestId, userId).orElseThrow(() -> new NotFoundException("Запрос не найден или недоступен."));
        request.setStatus(StatusEventRequestUpdateResult.CANCELED);
        return mapToParticipationRequestDto(repository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(int userId, int eventId) {
        return mapToListParticipationRequestDto(repository.findParticipationRequestsByEventsWithRequests_IdAndEventsWithRequests_Initiator_Id(eventId, userId));
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusRequestsByUser(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        List<ParticipationRequest> requestList = repository.findParticipationRequestsByEventsWithRequests_IdAndEventsWithRequests_Initiator_Id(eventId, userId);

        if (requestList.isEmpty()){
            throw new NotFoundException("Событие не найдено или недоступно.");
        }

        for (ParticipationRequest request : requestList) {

            boolean conditionOne = (request.getEventsWithRequests().getParticipantLimit() == 0) || !request.getEventsWithRequests().isRequestModeration();
            boolean conditionTwo = request.getEventsWithRequests().getConfirmedRequests() >= request.getEventsWithRequests().getParticipantLimit();
            boolean conditionThree = request.getStatus().equals(StatusEventRequestUpdateResult.PENDING);

            if (conditionOne) {
                request.setStatus(StatusEventRequestUpdateResult.CONFIRMED);
                request.getEventsWithRequests().setConfirmedRequests(request.getEventsWithRequests().getConfirmedRequests() + 1);
            }

            if (conditionTwo) {
                request.setStatus(StatusEventRequestUpdateResult.CANCELED);
                throw new ConflictException("Достигнут лимит одобренных заявок.");
            }

            if (conditionThree) {
                request.setStatus(eventRequestStatusUpdateRequest.getStatus());
                if (request.getStatus().equals(StatusEventRequestUpdateResult.CONFIRMED)) {
                    request.getEventsWithRequests().setConfirmedRequests(request.getEventsWithRequests().getConfirmedRequests() + 1);
                }
            }
        }
        return mapToEventRequestStatusUpdateResult(requestList);
    }
}
