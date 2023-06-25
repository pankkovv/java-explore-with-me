package ru.practicum.main.requests.mapper;

import ru.practicum.main.events.model.Event;
import ru.practicum.main.requests.dto.ParticipationRequestDto;
import ru.practicum.main.requests.model.ParticipationRequest;
import ru.practicum.main.users.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestsMap {
    public static ParticipationRequest mapToParticipationRequest(ParticipationRequestDto participationRequestDto, Event event, User user) {
        return ParticipationRequest.builder()
                .created(LocalDateTime.parse(participationRequestDto.getCreated()))
                .event(event)
                .requester(user)
                .status(participationRequestDto.getStatus())
                .build();
    }

    public static ParticipationRequestDto mapToParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .created(participationRequest.getCreated().toString())
                .event(participationRequest.getEvent().getId())
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
}
