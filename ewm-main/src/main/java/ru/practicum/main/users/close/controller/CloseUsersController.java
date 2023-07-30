package ru.practicum.main.users.close.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.messages.LogMessages;
import ru.practicum.main.users.close.service.CloseUsersService;
import ru.practicum.main.users.dto.UserDto;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class CloseUsersController {
    @Autowired
    private CloseUsersService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{userId}/subscribe/{subsId}")

    public UserDto subscribe(@PathVariable(name = "userId") int userId,
                             @PathVariable(name = "subsId") int subsId) {
        log.debug(LogMessages.TRY_PRIVATE_POST_SUBSCRIPTIONS.label);
        return service.subscribe(userId, subsId);
    }

    @DeleteMapping(path = "/{userId}/unsubscribe/{subsId}")
    public UserDto unsubscribe(@PathVariable(name = "userId") int userId,
                               @PathVariable(name = "subsId") int subsId) {
        log.debug(LogMessages.TRY_PRIVATE_DELETE_SUBSCRIPTIONS.label);
        return service.unsubscribe(userId, subsId);
    }

    @GetMapping(path = "/{userId}/subscription/events")
    public List<EventShortDto> subscriptionEvents(@PathVariable(name = "userId") int userId,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        log.debug(LogMessages.TRY_PRIVATE_GET_SUBSCRIPTIONS.label);
        return service.subscriptionEvents(userId, from, size);
    }

    @GetMapping(path = "/{userId}/subscriber/events")
    public List<EventShortDto> subscriberEvents(@PathVariable(name = "userId") int userId,
                                                @RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        log.debug(LogMessages.TRY_PRIVATE_GET_SUBSCRIBERS.label);
        return service.subscriberEvents(userId, from, size);
    }

    @GetMapping(path = "/{userId}/subscription/{subsId}/events")
    public List<EventShortDto> subscriptionByIdEvents(@PathVariable(name = "userId") int userId,
                                                      @PathVariable(name = "subsId") int subsId,
                                                      @RequestParam(name = "from", defaultValue = "0") int from,
                                                      @RequestParam(name = "size", defaultValue = "10") int size) {
        log.debug(LogMessages.TRY_PRIVATE_GET_SUBSCRIPTIONS_ID.label);
        return service.subscriptionByIdEvents(userId, subsId, from, size);
    }

    @GetMapping(path = "/{userId}/subscriber/{subsId}/events")
    public List<EventShortDto> subscriberByIdEvents(@PathVariable(name = "userId") int userId,
                                                    @PathVariable(name = "subsId") int subsId,
                                                    @RequestParam(name = "from", defaultValue = "0") int from,
                                                    @RequestParam(name = "size", defaultValue = "10") int size) {
        log.debug(LogMessages.TRY_PRIVATE_GET_SUBSCRIBERS_ID.label);
        return service.subscriberByIdEvents(userId, subsId, from, size);
    }
}
