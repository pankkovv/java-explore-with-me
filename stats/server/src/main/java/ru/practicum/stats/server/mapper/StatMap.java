package ru.practicum.stats.server.mapper;

import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.ResponseDto;
import ru.practicum.stats.server.model.Stat;

public class StatMap {
    public static Stat objFromDto (RequestDto requestDto) {
        return Stat.builder()
                .app(requestDto.getApp())
                .uri(requestDto.getUri())
                .ip(requestDto.getIp())
                .build();
    }

    public static RequestDto objToRequestDto (Stat stat) {
        return RequestDto.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .ip(stat.getIp())
                .build();
    }

    public static ResponseDto objToResponseDto (Stat stat){
        return ResponseDto.builder()
                .app(stat.getApp())
                .uri(stat.getUri())
                .hits(7)
                .build();
    }
}
