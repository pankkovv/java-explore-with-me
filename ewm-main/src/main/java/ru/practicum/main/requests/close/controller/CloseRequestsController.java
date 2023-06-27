package ru.practicum.main.requests.close.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.requests.close.service.CloseRequestsService;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.requests.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class CloseRequestsController {
    @Autowired
    private CloseRequestsService service;

    @GetMapping(path = "{userId}/requests")
    public List<ParticipationRequestDto> getRequestsByUserOtherEvents(@PathVariable(name = "userId") int userId) {
        return service.getRequestsByUserOtherEvents(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "{userId}/requests")
    public ParticipationRequestDto createRequestsByUserOtherEvents(@PathVariable(name = "userId") int userId,
                                                                   @RequestParam(name = "eventId") int eventId) {
        return service.createRequestsByUserOtherEvents(userId, eventId);
    }

    @PatchMapping(path = "{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestsByUserOtherEvents(@PathVariable(name = "userId") int userId,
                                                                   @PathVariable(name = "requestId") int requestId) {
        return service.cancelRequestsByUserOtherEvents(userId, requestId);
    }

    @GetMapping(path = "{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable(name = "userId") int userId,
                                                           @PathVariable(name = "eventId") int eventId) {
        return service.getRequestsByUser(userId, eventId);
    }

    @PatchMapping(path = "{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeStatusRequestsByUser(@PathVariable(name = "userId") int userId,
                                                                     @PathVariable(name = "eventId") int eventId,
                                                                     @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return service.changeStatusRequestsByUser(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
