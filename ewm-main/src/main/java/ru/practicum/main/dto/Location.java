package ru.practicum.main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Location {
    private float lat;
    private float lon;
}
