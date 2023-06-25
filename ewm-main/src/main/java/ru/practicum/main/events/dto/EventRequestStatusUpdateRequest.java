package ru.practicum.main.events.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.main.events.enums.StatusEventRequestUpdateResult;

import java.util.List;

@Data
@Builder
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private StatusEventRequestUpdateResult status;
}
