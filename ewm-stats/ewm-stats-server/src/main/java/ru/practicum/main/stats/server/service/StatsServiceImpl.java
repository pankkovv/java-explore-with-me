package ru.practicum.main.stats.server.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.stats.dto.RequestDto;
import ru.practicum.main.stats.dto.ResponseDto;
import ru.practicum.main.stats.server.exception.NotStatException;
import ru.practicum.main.stats.server.exception.TimestampException;
import ru.practicum.main.stats.server.mapper.StatsMapper;
import ru.practicum.main.stats.server.messages.ExceptionMessages;
import ru.practicum.main.stats.server.messages.LogMessages;
import ru.practicum.main.stats.server.model.Stats;
import ru.practicum.main.stats.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Slf4j
@Transactional
public class StatsServiceImpl implements StatsService {
    @Autowired
    private StatsRepository repository;

    @Override
    public ResponseDto hit(RequestDto requestDto) {
        Stats state = StatsMapper.mapToStat(requestDto);
        state.setTimestamp(LocalDateTime.now());
        log.debug(LogMessages.POST_HIT.label);
        return StatsMapper.mapToResponseDto(repository.save(state));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseDto> stats(String start, String end, List<String> uri, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timeStart = LocalDateTime.parse(start, formatter);
        LocalDateTime timeEnd = LocalDateTime.parse(end, formatter);
        List<ResponseDto> listResponseStat = new ArrayList<>();

        if (timeStart.isAfter(timeEnd)) {
            throw new TimestampException(ExceptionMessages.START_IS_AFTER_END.label);
        }

        if (uri != null && !uri.isEmpty()) {
            if (unique) {
                for (String u : uri) {
                    listResponseStat.addAll(repository.findStatUriUnique(timeStart, timeEnd, u));
                }
            } else {
                for (String u : uri) {
                    listResponseStat.addAll(repository.findStatUri(timeStart, timeEnd, u));
                }
            }
        } else {
            if (unique) {
                listResponseStat.addAll(repository.findStatUnique(timeStart, timeEnd));
            } else {
                listResponseStat.addAll(repository.findStat(timeStart, timeEnd));
            }
        }

        if (listResponseStat.isEmpty()) {
            throw new NotStatException(ExceptionMessages.NOT_FOUND_STATE.label);
        } else {
            log.debug(LogMessages.GET_STATS.label);
            return listResponseStat.stream().sorted(Comparator.comparing(ResponseDto::getHits).reversed()).collect(Collectors.toList());
        }
    }
}
