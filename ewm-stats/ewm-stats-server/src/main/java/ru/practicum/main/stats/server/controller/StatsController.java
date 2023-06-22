package ru.practicum.main.stats.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.stats.dto.RequestDto;
import ru.practicum.main.stats.dto.ResponseDto;
import ru.practicum.main.stats.server.messages.LogMessages;
import ru.practicum.main.stats.server.service.StatsService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Validated
@RestController
public class StatsController {
    private final StatsService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/hit")
    public ResponseDto hit(@Valid @RequestBody RequestDto requestDto) {
        log.debug(LogMessages.TRY_POST_HIT.label);
        return service.hit(requestDto);
    }

    @GetMapping(path = "/stats")
    public List<ResponseDto> stats(@RequestParam(name = "start") String start,
                                   @RequestParam(name = "end") String end,
                                   @RequestParam(name = "uris", required = false) List<String> uris,
                                   @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        log.debug(LogMessages.TRY_GET_STATS.label);
        return service.stats(start, end, uris, unique);
    }
}
