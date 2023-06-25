package ru.practicum.main.compilations.open.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.repository.CompilationsRepository;
import ru.practicum.main.events.dto.EventFullDto;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.compilations.mapper.CompMap.mapToCompilationDto;
import static ru.practicum.main.compilations.mapper.CompMap.mapToListCompilationDto;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class OpenCompilationsServiceImpl implements OpenCompilationsService {
    @Autowired
    private CompilationsRepository repository;
    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        Pageable page = paged(from, size);
        List<EventFullDto> listEventFullDto = new ArrayList<>();
        return mapToListCompilationDto(repository.findByPinnedContaining(pinned, page), listEventFullDto);
    }

    @Override
    public CompilationDto getCompilationsById(int compId) {
        List<EventFullDto> listEventFullDto = new ArrayList<>();
        return mapToCompilationDto(repository.getById(compId), listEventFullDto);
    }

    private Pageable paged(Integer from, Integer size) {
        Pageable page;
        if (from != null && size != null) {
            if (from < 0 || size < 0) {
                throw new RuntimeException();
            }
            page = PageRequest.of(from > 0 ? from / size : 0, size);
        } else {
            page = PageRequest.of(0, 4);
        }
        return page;
    }
}
