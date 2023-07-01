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
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.messages.LogMessages;

import java.util.List;

import static ru.practicum.main.compilations.mapper.CompMap.mapToCompilationsDto;
import static ru.practicum.main.compilations.mapper.CompMap.mapToListCompilationsDto;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class OpenCompilationsServiceImpl implements OpenCompilationsService {
    @Autowired
    private CompilationsRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<CompilationDto> getCompilations(String pinned, int from, int size) {
        Pageable page = paged(from, size);
        log.debug(LogMessages.PUBLIC_GET_COMPILATIONS.label);
        return mapToListCompilationsDto(repository.findCompilationsByPinnedIs(Boolean.parseBoolean(pinned), page));
    }

    @Transactional(readOnly = true)
    @Override
    public CompilationDto getCompilationsById(int compId) {
        log.debug(LogMessages.PUBLIC_GET_COMPILATIONS_ID.label, compId);
        return mapToCompilationsDto(repository.findById(compId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_COMPILATIONS_EXCEPTION.label)));
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