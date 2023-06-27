package ru.practicum.main.events.admin.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.categories.admin.service.AdminCategoriesService;
import ru.practicum.main.categories.model.Category;
import ru.practicum.main.events.dto.AdminEventRequests;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.UpdateEventAdminRequest;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.events.model.QEvent;
import ru.practicum.main.events.repository.EventsRepository;
import ru.practicum.main.locations.model.Location;
import ru.practicum.main.locations.service.LocationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.events.mapper.EventsMap.mapToEventFullDto;
import static ru.practicum.main.events.mapper.EventsMap.mapToListEventFullDto;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class AdminEventsServiceImpl implements AdminEventsService {
    @Autowired
    private EventsRepository repository;
    @Autowired
    private AdminCategoriesService categoriesService;
    @Autowired
    private LocationService locationService;

    @Override
    public List<EventFullDto> findEvents(AdminEventRequests requests) {
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();

        if (requests.hasUsers()) {
            for (Integer id : requests.getUsers()) {
                conditions.add(event.initiator.id.eq(id));
            }
        }

        if (requests.hasStates()) {
            for (Integer id : requests.getCategories()) {
                conditions.add(event.category.id.eq(id));
            }
        }

        if (requests.hasCategories()) {
            for (Integer id : requests.getCategories()) {
                conditions.add(event.category.id.eq(id));
            }
        }

        if (requests.getRangeStart() != null && requests.getRangeEnd() != null) {
            conditions.add(event.eventDate.between(requests.getRangeStart(), requests.getRangeEnd()));
        }

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        PageRequest pageRequest = PageRequest.of(requests.getFrom(), requests.getSize());

        Page<Event> eventsPage = repository.findAll(finalCondition, pageRequest);
        return mapToListEventFullDto(eventsPage);
    }

    @Override
    public EventFullDto changeEvents(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = repository.findById(eventId).orElseThrow();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
        if (updateEventAdminRequest.getCategory() != null) {
            Category category = categoriesService.findCategoriesById(updateEventAdminRequest.getCategory());
            event.setCategory(category);
        }
        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }
        if (updateEventAdminRequest.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateEventAdminRequest.getEventDate(), formatter));
        }
        if (updateEventAdminRequest.getLocation() != null) {
            Location location = locationService.save(updateEventAdminRequest.getLocation());
            event.setLocation(location);
        }
        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }
        if (updateEventAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
        if (updateEventAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }
        if (updateEventAdminRequest.getStateAction() != null) {
            switch (updateEventAdminRequest.getStateAction()) {
                case PUBLISH_EVENT:
                    event.setState(EventStatus.PUBLISHED);
                    break;
                case REJECT_EVENT:
                    event.setState(EventStatus.CANCELED);
                    break;
            }
        }
        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }

        return mapToEventFullDto(repository.save(event));
    }
}
