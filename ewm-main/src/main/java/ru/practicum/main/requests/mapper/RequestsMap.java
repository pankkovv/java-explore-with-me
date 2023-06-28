package ru.practicum.main.requests.mapper;

import ru.practicum.main.events.model.Event;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.requests.dto.ParticipationRequestDto;
import ru.practicum.main.requests.model.ParticipationRequest;
import ru.practicum.main.requests.model.StatusEventRequestUpdateResult;
import ru.practicum.main.users.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RequestsMap {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequest mapToParticipationRequest(ParticipationRequestDto participationRequestDto, Event event, User user) {
        return ParticipationRequest.builder()
                .created(LocalDateTime.parse(participationRequestDto.getCreated(), formatter))
                .eventsWithRequests(event)
                .requester(user)
                .status(participationRequestDto.getStatus())
                .build();
    }

    public static ParticipationRequestDto mapToParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .id(participationRequest.getId())
                .created(participationRequest.getCreated().toString().replace("T", " "))
                .event(participationRequest.getEventsWithRequests().getId())
                .requester(participationRequest.getRequester().getId())
                .status(participationRequest.getStatus())
                .build();
    }

    public static List<ParticipationRequestDto> mapToListParticipationRequestDto(List<ParticipationRequest> listParticipationRequest) {
        List<ParticipationRequestDto> listParticipationRequestDto = new ArrayList<>();
        for (ParticipationRequest request : listParticipationRequest) {
            listParticipationRequestDto.add(mapToParticipationRequestDto(request));
        }
        return listParticipationRequestDto;
    }

    public static EventRequestStatusUpdateResult mapToEventRequestStatusUpdateResult(List<ParticipationRequest> requestList) {
        List<ParticipationRequestDto> requestsConfirmed = new ArrayList<>();
        List<ParticipationRequestDto> requestsRejected = new ArrayList<>();

        for (ParticipationRequest request : requestList) {
            if (request.getStatus().equals(StatusEventRequestUpdateResult.CONFIRMED)) {
                requestsConfirmed.add(mapToParticipationRequestDto(request));
            }
            if (request.getStatus().equals(StatusEventRequestUpdateResult.REJECTED)) {
                requestsRejected.add(mapToParticipationRequestDto(request));
            }
        }

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(requestsConfirmed)
                .rejectedRequests(requestsRejected)
                .build();
    }
}
