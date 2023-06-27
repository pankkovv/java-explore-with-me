package ru.practicum.main.events.close.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.close.service.CloseEventsService;
import ru.practicum.main.events.dto.*;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;

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

    @ResponseStatus(HttpStatus.CREATED)
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
}
