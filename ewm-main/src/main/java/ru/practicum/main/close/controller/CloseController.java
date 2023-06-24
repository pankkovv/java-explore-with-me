package ru.practicum.main.close.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class CloseController {
    @GetMapping(path = "/{userId}/events")
    public List<EventShortDto> getEventsByUser(@PathVariable(name = "userId") int userId,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        return null;
    }

    @PostMapping(path = "/{userId}/events")
    public EventFullDto createEvents(@PathVariable(name = "userId") int userId,
                                     @RequestBody @Valid NewEventDto newEventDto) {
        return null;
    }

    @GetMapping(path = "/{userId}/events/{eventId}")
    public EventFullDto getEventsByUserFullInfo(@PathVariable(name = "userId") int userId,
                                                @PathVariable(name = "eventId") int eventId) {
        return null;
    }

    @PatchMapping(path = "{userId}/events/{eventId}")
    public EventFullDto changeEventsByUser(@PathVariable(name = "userId") int userId,
                                           @PathVariable(name = "eventId") int eventId,
                                           @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @GetMapping(path = "{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUser(@PathVariable(name = "userId") int userId,
                                                           @PathVariable(name = "eventId") int eventId) {
        return null;
    }

    @PatchMapping(path = "{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeStatusRequestsByUser(@PathVariable(name = "userId") int userId,
                                                                     @PathVariable(name = "eventId") int eventId,
                                                                     @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }

    @GetMapping(path = "{userId}/requests")
    public List<ParticipationRequestDto> getRequestsByUserOtherEvents(@PathVariable(name = "userId") int userId) {
        return null;
    }

    @PostMapping(path = "{userId}/requests/{eventId}")
    public ParticipationRequestDto createRequestsByUserOtherEvents(@PathVariable(name = "userId") int userId,
                                                                   @PathVariable(name = "eventId") int eventId) {
        return null;
    }

    @PatchMapping(path = "{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestsByUserOtherEvents(@PathVariable(name = "userId") int userId,
                                                                   @PathVariable(name = "requestId") int requestId) {
        return null;
    }
}
