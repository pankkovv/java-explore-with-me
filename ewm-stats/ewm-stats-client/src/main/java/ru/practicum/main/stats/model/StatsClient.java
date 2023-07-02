package ru.practicum.main.stats.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.main.stats.client.BaseClient;
import ru.practicum.main.stats.dto.RequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    @Value("${stats-server.url}")
    private String serverUrl;

    @Value("${main-app.name}")
    private String appMain;

    @Autowired
    public StatsClient(RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory())
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void hit(HttpServletRequest request) {
        RequestDto requestDto = RequestDto.builder()
                .app(appMain)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .build();
        post(serverUrl + "/hit", requestDto);
    }

    public ResponseEntity<Object> stats(String start,
                                        String end,
                                        List<String> uris,
                                        boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );

        return get(serverUrl + "/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }
}