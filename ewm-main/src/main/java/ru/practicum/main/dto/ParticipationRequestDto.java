package ru.practicum.main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipationRequestDto {
    private String created;
    private int event;
    private int id;
    private int requester;
    private String status;
}
