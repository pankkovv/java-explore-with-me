package ru.practicum.stats.server.service;

import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.RequestParams;
import ru.practicum.stats.dto.ResponseDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface StatsService {
    void hit(RequestDto requestDto);
    List<ResponseDto> stats(RequestParams requestParams) throws UnsupportedEncodingException;
}
