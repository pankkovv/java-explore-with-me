package ru.practicum.stats.server;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.RequestParams;
import ru.practicum.stats.dto.ResponseDto;
import ru.practicum.stats.server.service.StatsService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Validated
@RestController
public class StatsController {
    private final StatsService service;

    @PostMapping(path = "/hit")
    public void hit(@RequestBody @Valid RequestDto requestDto) {
        service.hit(requestDto);
    }

    @GetMapping(path = "/stats")
    public List<ResponseDto> stats(@RequestParam(name = "start") String start,
                             @RequestParam(name = "end") String end,
                             @RequestParam(name = "uris", required = false) String[] uris,
                             @RequestParam(name = "unique", defaultValue = "false") boolean unique) throws UnsupportedEncodingException {
        return service.stats(RequestParams.of(start, end, uris, unique));
    }
}
