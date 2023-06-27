package ru.practicum.main.events.open.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.OpenEventRequests;
import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.events.model.QEvent;
import ru.practicum.main.events.repository.EventsRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.events.mapper.EventsMap.*;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class OpenEventsServiceImpl implements OpenEventsService {
    @Autowired
    private EventsRepository repository;

    @Override
    public List<EventShortDto> getEvents(OpenEventRequests requests) {
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
            conditions.add(event.eventDate.between(requests.getRangeStart(), requests.getRangeEnd()));
        }
//
//        if (requests.getOnlyAvailable() != null) {
//            conditions.add(event.state.eq(EventStatus.PUBLISHED));
//        }

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        Sort sort = makeOrderByClause(requests.getSort());
        PageRequest pageRequest = PageRequest.of(requests.getFrom(), requests.getSize(), sort);

        Page<Event> eventsPage = repository.findAll(finalCondition, pageRequest);
        return mapToListEventShortDto(eventsPage);
    }

    @Override
    public EventFullDto getEventsById(int eventId) {
        return mapToEventFullDto(repository.findById(eventId).orElseThrow());
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
