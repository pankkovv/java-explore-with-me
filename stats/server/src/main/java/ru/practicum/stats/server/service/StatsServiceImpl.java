package ru.practicum.stats.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.RequestParams;
import ru.practicum.stats.dto.ResponseDto;
import ru.practicum.stats.server.mapper.StatMap;
import ru.practicum.stats.server.repository.StatsRepository;

public class StatsServiceImpl implements StatsService {
    @Autowired
    private StatsRepository repository;

    @Override
    public void hit(RequestDto requestDto) {
        repository.save(StatMap.objFromDto(requestDto));
    }

    @Override
    public ResponseDto stats(RequestParams requestParams) {
        return StatMap.objToResponseDto(repository.getById(7L));
    }
}
