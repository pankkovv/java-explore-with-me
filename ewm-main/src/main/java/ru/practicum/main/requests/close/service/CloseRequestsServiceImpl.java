package ru.practicum.main.requests.close.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.events.enums.EventStatus;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.requests.dto.ParticipationRequestDto;
import ru.practicum.main.requests.model.ParticipationRequest;
import ru.practicum.main.requests.repository.RequestsRepository;
import ru.practicum.main.users.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.requests.mapper.RequestsMap.mapToListParticipationRequestDto;
import static ru.practicum.main.requests.mapper.RequestsMap.mapToParticipationRequestDto;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class CloseRequestsServiceImpl implements CloseRequestsService {
    @Autowired
    private RequestsRepository repository;

    @Override
    public List<ParticipationRequestDto> getRequestsByUserOtherEvents(int userId) {
        return mapToListParticipationRequestDto(repository.findParticipationRequestsByRequester_Id(userId));
    }

    @Override
    public ParticipationRequestDto createRequestsByUserOtherEvents(int userId, int eventId) {
        Event event = new Event();
        User user = new User();
        ParticipationRequest request = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(EventStatus.PENDING)
                .build();
        return mapToParticipationRequestDto(repository.save(request));
    }

    @Override
    public ParticipationRequestDto cancelRequestsByUserOtherEvents(int userId, int requestId) {
        ParticipationRequest request = repository.findParticipationRequestByIdAndRequester_Id(requestId, userId);
        request.setStatus(EventStatus.CANCELED);
        return mapToParticipationRequestDto(repository.save(request));
    }
}
