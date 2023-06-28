package ru.practicum.main.compilations.admin.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.dto.UpdateCompilationRequest;
import ru.practicum.main.compilations.model.Compilations;
import ru.practicum.main.compilations.repository.CompilationsRepository;
import ru.practicum.main.events.close.service.CloseEventsService;
import ru.practicum.main.events.model.Event;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.compilations.mapper.CompMap.mapToCompilations;
import static ru.practicum.main.compilations.mapper.CompMap.mapToCompilationsDto;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    @Autowired
    CompilationsRepository compilationsRepository;
    @Autowired
    private CloseEventsService eventsService;

    @Override
    public CompilationDto createCompilations(NewCompilationDto newCompilationDto) {
        List<Event> eventList = new ArrayList<>();

        for (Integer eventId : newCompilationDto.getEvents()) {
            eventList.add(eventsService.getEventById(eventId));
        }

        return mapToCompilationsDto(compilationsRepository.save(mapToCompilations(newCompilationDto, eventList)));
    }

    @Override
    public void deleteCompilations(int compId) {
        compilationsRepository.deleteById(compId);
    }

    @Override
    public CompilationDto changeCompilations(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilations oldCompilations = compilationsRepository.findById(compId).orElseThrow();

        if (updateCompilationRequest.getEvents() != null && !updateCompilationRequest.getEvents().isEmpty()) {
            List<Event> eventList = new ArrayList<>();

            for (Integer eventId : updateCompilationRequest.getEvents()) {
                eventList.add(eventsService.getEventById(eventId));
            }

            oldCompilations.setEventsWithCompilations(eventList);
        }
        if (updateCompilationRequest.getPinned() != null) {
            oldCompilations.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null && !updateCompilationRequest.getTitle().isEmpty()) {
            oldCompilations.setTitle(updateCompilationRequest.getTitle());
        }

        return mapToCompilationsDto(compilationsRepository.save(oldCompilations));
    }

}
