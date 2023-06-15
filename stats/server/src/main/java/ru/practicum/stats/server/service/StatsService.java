package ru.practicum.stats.server.service;

import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.RequestParams;
import ru.practicum.stats.dto.ResponseDto;

@Service
public interface StatsService {
    void hit(RequestDto requestDto);
    ResponseDto stats(RequestParams requestParams);
}
