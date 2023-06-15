package ru.practicum.stats.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.RequestParams;
import ru.practicum.stats.dto.ResponseDto;
import ru.practicum.stats.server.service.StatsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    @Autowired
    StatsService service;


    @PostMapping(path = "/hit")
    public void hit(RequestDto requestDto) {
        service.hit(requestDto);
    }

    @GetMapping(path = "/stats")
    public ResponseDto stats(@RequestParam(name = "start") String start,
                             @RequestParam(name = "end") String end,
                             @RequestParam(name = "uris") List<String> uris,
                             @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        return service.stats(RequestParams.of(start, end, uris, unique));
    }
}
