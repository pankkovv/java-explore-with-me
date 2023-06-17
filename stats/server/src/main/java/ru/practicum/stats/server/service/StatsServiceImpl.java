package ru.practicum.stats.server.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.stats.dto.RequestDto;
import ru.practicum.stats.dto.RequestParams;
import ru.practicum.stats.dto.ResponseDto;
import ru.practicum.stats.server.mapper.StatMap;
import ru.practicum.stats.server.model.Stat;
import ru.practicum.stats.server.repository.StatsRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    public void hit(RequestDto requestDto) {
        Stat state = StatMap.objFromDto(requestDto);
        state.setTimestamp(LocalDateTime.now());
        repository.save(state);
    }

    @Override
    public List<ResponseDto> stats(RequestParams requestParams) throws UnsupportedEncodingException {
        List<Stat> listStats = new ArrayList<>();

//        if(requestParams.isUnique()){
//            if(requestParams.getUris() != null){
//                for(String s : requestParams.getUris()){
//                    repository.findStatUriUnique(LocalDateTime.parse(requestParams.getStart(), formatter), LocalDateTime.parse(requestParams.getStart(), formatter), s);
//                }
//            } else {
//                repository.findStatUnique(LocalDateTime.parse(requestParams.getStart(), formatter), LocalDateTime.parse(requestParams.getStart(), formatter));
//            }
//        } else {
//            if(requestParams.getUris() != null){
//                for(String s : requestParams.getUris()){
//                    repository.findUriStat(LocalDateTime.parse(requestParams.getStart(), formatter), LocalDateTime.parse(requestParams.getStart(), formatter), s);
//                }
//            } else {
//                repository.findStat(LocalDateTime.parse(requestParams.getStart(), formatter), LocalDateTime.parse(requestParams.getStart(), formatter));
//            }
//        }
        repository.findStatUnique(LocalDateTime.parse(URLEncoder.encode(requestParams.getStart(), StandardCharsets.UTF_8)), LocalDateTime.parse(URLEncoder.encode(requestParams.getStart(), StandardCharsets.UTF_8)));


        return StatMap.objToListResponseDto(listStats);
    }
}
