package ru.practicum.main.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    @PostMapping(path = "/categories")
    public void createCategories() {
    }

    @DeleteMapping(path = "/categories/{catId}")
    public void deleteCategories() {
    }

    @PatchMapping(path = "/categories/{catId}")
    public void changeCategories() {
    }

    @GetMapping(path = "/events")
    public void findEvents() {
    }

    @PatchMapping(path = "/events/{eventId}")
    public void changeEvents() {
    }

    @GetMapping(path = "/users")
    public void getUsers() {
    }

    @PostMapping(path = "/users")
    public void createUsers() {
    }

    @DeleteMapping(path = "/users/{userId}")
    public void deleteUsers() {
    }

    @PostMapping(path = "/compilations")
    public void createCompilations() {
    }

    @DeleteMapping(path = "/compilations/{compId}")
    public void deleteCompilations() {
    }

    @PatchMapping(path = "/compilations/{compId}")
    public void changeCompilations() {
    }
}
