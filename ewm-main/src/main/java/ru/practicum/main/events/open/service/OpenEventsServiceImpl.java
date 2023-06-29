package ru.practicum.main.events.open.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.events.dto.OpenEventRequests;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.events.model.QEvent;
import ru.practicum.main.events.repository.EventsRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidTimeException;
import ru.practicum.main.stats.dto.RequestDto;
import ru.practicum.main.stats.model.StatsClient;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.events.mapper.EventsMap.mapToEventFullDto;
import static ru.practicum.main.events.mapper.EventsMap.mapToListEventShortDto;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OpenEventsServiceImpl implements OpenEventsService {
    @Autowired
    private EventsRepository repository;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getEvents(OpenEventRequests requests, HttpServletRequest request) {
        statsClient.hit(RequestDto.builder().app(request.getPathInfo()).uri(request.getRequestURI()).ip(request.getRemoteAddr()).build());

        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();

        if (requests.getText() != null) {
            conditions.add(event.annotation.containsIgnoreCase(requests.getText()).or(event.description.containsIgnoreCase(requests.getText())));
        }

        if (requests.hasCategories()) {
            for (Integer id : requests.getCategories()) {
                conditions.add(event.category.id.eq(id));
            }
        }

        if (requests.getPaid() != null) {
            conditions.add(event.paid.eq(requests.getPaid()));
        }

        if (requests.getRangeStart() != null && requests.getRangeEnd() != null) {
            if (requests.getRangeStart().isAfter(requests.getRangeEnd())) {
                throw new ValidTimeException("Время старта не может быть позже времени окончания.");
            } else {
                conditions.add(event.eventDate.between(requests.getRangeStart(), requests.getRangeEnd()));
            }
        }

        if (requests.getOnlyAvailable() != null) {
            conditions.add(event.confirmedRequests.between(0, event.participantLimit.getType().getModifiers()));
        }

        PageRequest pageRequest = PageRequest.of(requests.getFrom(), requests.getSize());

        if (requests.getSort() != null) {
            Sort sort = makeOrderByClause(requests.getSort());
            pageRequest = PageRequest.of(requests.getFrom(), requests.getSize(), sort);
        }

        conditions.add(event.state.eq(EventStatus.PUBLISHED));

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        Page<Event> eventsPage = repository.findAll(finalCondition, pageRequest);

        if (eventsPage.isEmpty()) {
            throw new NotFoundException("");
        }

        for (Event eventViewed : eventsPage) {
            eventViewed.setViews(eventViewed.getViews() + 1);
        }
        repository.saveAll(eventsPage);

        return mapToListEventShortDto(eventsPage);
    }

    @Override
    public EventFullDto getEventsById(int eventId, HttpServletRequest request) {
        statsClient.hit(RequestDto.builder().app("ewm-main-service").uri(request.getRequestURI()).ip(request.getRemoteAddr()).build());

        Event event = repository.findEventsByIdAndStateIs(eventId, EventStatus.PUBLISHED).orElseThrow(() -> new NotFoundException("Запись о событии не найдена."));
        ResponseEntity<Object> response = statsClient.stats(event.getCreatedOn().toString(), event.getEventDate().toString(), List.of(request.getRequestURI()), true);
        event.setViews(response.getStatusCodeValue());
        repository.save(event);
        return mapToEventFullDto(event);
    }

    private Sort makeOrderByClause(OpenEventRequests.Sort sort) {
        switch (sort) {
            case EVENT_DATE:
                return Sort.by("eventDate").descending();
            case VIEWS:
                return Sort.by("views").descending();
            default:
                return Sort.by("publishedOn").descending();
        }
    }
}
