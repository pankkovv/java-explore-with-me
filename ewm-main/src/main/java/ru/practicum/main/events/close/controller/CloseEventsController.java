package ru.practicum.main.events.close.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.close.service.CloseEventsService;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.events.dto.NewEventDto;
import ru.practicum.main.events.dto.UpdateEventUserRequest;
import ru.practicum.main.events.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.events.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class CloseEventsController {
    @Autowired
    private CloseEventsService service;
    @GetMapping(path = "/{userId}/events")
    public List<EventShortDto> getEventsByUser(@PathVariable(name = "userId") int userId,
                                               @RequestParam(name = "from", defaultValue = "0") int from,
                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        return service.getEventsByUser(userId, from, size);
    }

    @PostMapping(path = "/{userId}/events")
    public EventFullDto createEvents(@PathVariable(name = "userId") int userId,
                                     @RequestBody @Valid NewEventDto newEventDto) {
        return service.createEvents(userId, newEventDto);
    }

    @GetMapping(path = "/{userId}/events/{eventId}")
    public EventFullDto getEventsByUserFullInfo(@PathVariable(name = "userId") int userId,
                                                @PathVariable(name = "eventId") int eventId) {
        return service.getEventsByUserFullInfo(userId, eventId);
    }

    @PatchMapping(path = "{userId}/events/{eventId}")
    public EventFullDto changeEventsByUser(@PathVariable(name = "userId") int userId,
                                           @PathVariable(name = "eventId") int eventId,
                                           @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return service.changeEventsByUser(userId, eventId, updateEventUserRequest);
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
