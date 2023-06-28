package ru.practicum.main.events.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
public class OpenEventRequests {
    private String text;
    private List<Integer> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private Sort sort;
    private Integer from;
    private Integer size;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static OpenEventRequests of(String text,
                                       List<Integer> categories,
                                       boolean paid,
                                       String rangeStart,
                                       String rangeEnd,
                                       boolean onlyAvailable,
                                       String sort,
                                       Integer from,
                                       Integer size) {
        OpenEventRequests request = new OpenEventRequests();
        request.setText(text);
        request.setPaid(paid);

        if (rangeStart != null) {
            request.setRangeStart(LocalDateTime.parse(rangeStart, formatter));
        }

        if (rangeEnd != null) {
            request.setRangeEnd(LocalDateTime.parse(rangeEnd, formatter));
        }

        request.setOnlyAvailable(onlyAvailable);

        if (sort != null) {
            request.setSort(Sort.valueOf(sort.toUpperCase()));
        }

        request.setFrom(from);
        request.setSize(size);

        if (categories != null) {
            request.setCategories(categories);
        }

        return request;
    }

    public boolean hasCategories() {
        return categories != null && !categories.isEmpty();
    }

    public enum Sort {
        EVENT_DATE,
        VIEWS
    }
}