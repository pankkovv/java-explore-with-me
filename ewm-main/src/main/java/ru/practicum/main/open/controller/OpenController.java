package ru.practicum.main.open.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OpenController {
    @GetMapping(path = "/compilations")
    public void getCompilations() {
    }

    @GetMapping(path = "/compilations/{compId}")
    public void getCompilationsById() {
    }

    @GetMapping(path = "/categories")
    public void getCategories() {
    }

    @GetMapping(path = "/categories/{catId}")
    public void getCategoriesById() {
    }

    @GetMapping(path = "/events")
    public void getEvents() {
    }

    @GetMapping(path = "/events/{eventId}")
    public void getEventsById() {
    }
}
