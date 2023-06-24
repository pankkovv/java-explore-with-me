package ru.practicum.main.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.dto.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    @PostMapping(path = "/categories")
    public CategoryDto createCategories(@RequestBody @Valid CategoryDto categoryDto) {
        return null;
    }

    @DeleteMapping(path = "/categories/{catId}")
    public void deleteCategories(@PathVariable(name = "catId") int catId) {
    }

    @PatchMapping(path = "/categories/{catId}")
    public CategoryDto changeCategories(@PathVariable(name = "catId") int catId,
                                        @RequestBody @Valid CategoryDto categoryDto) {
        return null;
    }

    @GetMapping(path = "/events")
    public List<EventFullDto> findEvents(@RequestParam(name = "users", required = false) List<Integer> users,
                                         @RequestParam(name = "states", required = false) List<String> states,
                                         @RequestParam(name = "categories", required = false) List<Integer> categories,
                                         @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                         @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                         @RequestParam(name = "from", defaultValue = "0") int from,
                                         @RequestParam(name = "size", defaultValue = "10") int size) {
        return null;
    }

    @PatchMapping(path = "/events/{eventId}")
    public EventFullDto changeEvents(@PathVariable(name = "eventId") int eventId,
                                     @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        return null;
    }

    @GetMapping(path = "/users")
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Integer> ids,
                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        return null;
    }

    @PostMapping(path = "/users")
    public UserDto createUsers(@RequestBody @Valid NewUserRequest newUserRequest) {
        return null;
    }

    @DeleteMapping(path = "/users/{userId}")
    public void deleteUsers(@PathVariable(name = "userId") int userId) {
    }

    @PostMapping(path = "/compilations")
    public CompilationDto createCompilations(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return null;
    }

    @DeleteMapping(path = "/compilations/{compId}")
    public void deleteCompilations(@PathVariable(name = "compId") int compId) {
    }

    @PatchMapping(path = "/compilations/{compId}")
    public CompilationDto changeCompilations(@PathVariable(name = "compId") int compId,
                                             @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        return null;
    }
}
