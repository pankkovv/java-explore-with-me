package ru.practicum.stats.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {

    @PostMapping(path = "/hit")
    public ResponseEntity<Object> hit(){
     return null;
    }

    @GetMapping(path = "/stats")
    public ResponseEntity<Object> stats(){
        return null;
    }
}
