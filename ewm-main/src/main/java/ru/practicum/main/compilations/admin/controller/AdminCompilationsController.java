package ru.practicum.main.compilations.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilations.admin.service.AdminCompilationsService;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationsController {
    @Autowired
    AdminCompilationsService service;
    @PostMapping(path = "/compilations")
    public CompilationDto createCompilations(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return service.createCompilations(newCompilationDto);
    }

    @DeleteMapping(path = "/compilations/{compId}")
    public void deleteCompilations(@PathVariable(name = "compId") int compId) {
        service.deleteCompilations(compId);
    }

    @PatchMapping(path = "/compilations/{compId}")
    public CompilationDto changeCompilations(@PathVariable(name = "compId") int compId,
                                             @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        return service.changeCompilations(compId, updateCompilationRequest);
    }
}
