package ru.practicum.stats.server.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.ResponseDto;
import ru.practicum.stats.server.exception.NotStatException;
import ru.practicum.stats.server.exception.TimestampException;
import ru.practicum.stats.server.mapper.StatsMapper;
import ru.practicum.stats.server.messages.ExceptionMessages;
import ru.practicum.stats.server.model.Stats;
import ru.practicum.stats.server.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class StatsServiceImpl implements StatsService {
    @Autowired
    private StatsRepository repository;

    @Override
    public ResponseDto hit(RequestDto requestDto) {
        Stats state = StatsMapper.mapToStat(requestDto);
        state.setTimestamp(LocalDateTime.now());
        return StatsMapper.mapToResponseDto(repository.save(state));
    }

    @Override
    public List<ResponseDto> stats(String start, String end, String[] uri, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime timeStart = LocalDateTime.parse(start, formatter);
        LocalDateTime timeEnd = LocalDateTime.parse(end, formatter);
        List<ResponseDto> listResponseStat = new ArrayList<>();

        if (timeStart.isAfter(timeEnd)) {
            throw new TimestampException(ExceptionMessages.START_IS_AFTER_END.label);
        }

        if (uri != null) {
            for (String u : uri) {
                if (unique) {
                    listResponseStat.addAll(repository.findStatUriUnique(timeStart, timeEnd, u));
                }
                listResponseStat.addAll(repository.findStatUri(timeStart, timeEnd, u));
            }
        }

        if (unique) {
            listResponseStat.addAll(repository.findStatUnique(timeStart, timeEnd));
        }
        listResponseStat.addAll(repository.findStat(timeStart, timeEnd));


        if (listResponseStat.isEmpty()) {
            throw new NotStatException(ExceptionMessages.NOT_FOUND_STATE.label);
        } else {
            return listResponseStat;
        }
    }
}
