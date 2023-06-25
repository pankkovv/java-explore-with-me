package ru.practicum.main.requests.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.main.events.enums.EventStatus;

@Data
@Builder
public class ParticipationRequestDto {
    private String created;
    private int event;
    private int id;
    private int requester;
    private EventStatus status;
}
