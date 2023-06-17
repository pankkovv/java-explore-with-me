package ru.practicum.stats.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestParams {
    private String start;
    private String end;
    private String[] uris;
    private boolean unique;

    public static RequestParams of(String start,
                                   String end,
                                  String[] uris,
                                  boolean unique){
        RequestParams request = new RequestParams();
        request.setStart(start);
        request.setEnd(end);
        request.setUris(uris);
        request.setUnique(unique);
        return request;
    }
}
