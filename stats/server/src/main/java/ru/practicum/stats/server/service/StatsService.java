package ru.practicum.stats.server.service;

import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.ResponseDto;

import java.util.List;

public interface StatsService {
    ResponseDto hit(RequestDto requestDto);

    List<ResponseDto> stats(String start, String end, String[] uri, boolean unique);
}
