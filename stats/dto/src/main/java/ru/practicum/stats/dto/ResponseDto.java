package ru.practicum.stats.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private String app;
    private String uri;
    private int hits;
}
