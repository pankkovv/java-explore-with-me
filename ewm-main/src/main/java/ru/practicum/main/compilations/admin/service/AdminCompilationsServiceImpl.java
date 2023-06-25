package ru.practicum.main.compilations.admin.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;
import ru.practicum.main.compilations.model.Compilation;
import ru.practicum.main.compilations.repository.CompilationsRepository;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.model.Event;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.compilations.mapper.CompMap.mapToCompilationDto;
import static ru.practicum.main.compilations.mapper.CompMap.mapToCompilations;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    @Autowired
    CompilationsRepository repository;
    @Override
    public CompilationDto createCompilations(NewCompilationDto newCompilationDto) {
        Event event = new Event();
        List<EventFullDto> listEventFullDto = new ArrayList<>();
        Compilation compilation = mapToCompilations(newCompilationDto,event);
        return mapToCompilationDto(repository.save(compilation), listEventFullDto);
    }

    @Override
    public void deleteCompilations(int compId) {
        repository.deleteById(compId);
    }

    @Override
    public CompilationDto changeCompilations(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation oldCompilation = repository.findById(compId).orElseThrow();
        List<EventFullDto> listEventFullDto = new ArrayList<>();

        if(updateCompilationRequest.getEvents() != null && !updateCompilationRequest.getEvents().isEmpty()){
            Event event = new Event();
            oldCompilation.setEvents(event);
        }
        if(updateCompilationRequest.isPinned()){
            oldCompilation.setPinned(updateCompilationRequest.isPinned());
        }
        if(updateCompilationRequest.getTitle() != null && !updateCompilationRequest.getTitle().isEmpty()){
            oldCompilation.setTitle(updateCompilationRequest.getTitle());
        }

        return mapToCompilationDto(oldCompilation, listEventFullDto);
    }
}
