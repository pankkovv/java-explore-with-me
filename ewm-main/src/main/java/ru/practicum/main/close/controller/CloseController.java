package ru.practicum.main.close.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class CloseController {
    @GetMapping(path = "/{userId}/events")
    public void getEventsByUser() {
    }

    @PostMapping(path = "/{userId}/events")
    public void createEvents() {
    }

    @GetMapping(path = "/{userId}/events/{eventId}")
    public void getEventsByUserFullInfo() {
    }

    @PatchMapping(path = "{userId}/events/{eventId}")
    public void changeEventsByUser() {
    }

    @GetMapping(path = "{userId}/events/{eventId}/requests")
    public void getRequestsByUser() {
    }

    @PatchMapping(path = "{userId}/events/{eventId}/requests")
    public void changeStatusRequestsByUser() {
    }

    @GetMapping(path = "{userId}/requests")
    public void getRequestsByUserOtherEvents() {
    }

    @PostMapping(path = "{userId}/requests")
    public void createRequestsByUserOtherEvents() {
    }

    @PatchMapping(path = "{userId}/requests/{requestId}/cancel")
    public void cancelRequestsByUserOtherEvents() {
    }
}
