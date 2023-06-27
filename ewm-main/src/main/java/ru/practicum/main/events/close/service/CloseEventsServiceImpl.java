package ru.practicum.main.events.close.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.categories.model.Category;
import ru.practicum.main.categories.open.service.OpenCategoriesService;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.events.dto.NewEventDto;
import ru.practicum.main.events.dto.UpdateEventUserRequest;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.model.EventStatus;
import ru.practicum.main.events.repository.EventsRepository;
import ru.practicum.main.locations.model.Location;
import ru.practicum.main.locations.service.LocationService;
import ru.practicum.main.users.admin.service.AdminUsersServiceImpl;
import ru.practicum.main.users.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.categories.mapper.CatMap.mapToCategory;
import static ru.practicum.main.events.mapper.EventsMap.*;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class CloseEventsServiceImpl implements CloseEventsService {
    @Autowired
    private EventsRepository repository;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AdminUsersServiceImpl usersService;
    @Autowired
    private OpenCategoriesService categoriesService;

    @Override
    public List<EventShortDto> getEventsByUser(int userId, int from, int size) {
        Pageable page = paged(from, size);
        return mapToListEventShortDto(repository.findEventsByInitiator_Id(userId, page));
    }

    @Override
    public EventFullDto createEvents(int userId, NewEventDto newEventDto) {
        User user = usersService.getUserById(userId);
        Category category = categoriesService.getCatById(newEventDto.getCategory());
        Location location = locationService.save(newEventDto.getLocation());

        return mapToEventFullDto(repository.save(mapToEvent(newEventDto, category, user)));
    }

    @Override
    public EventFullDto getEventsByUserFullInfo(int userId, int eventId) {
        return mapToEventFullDto(repository.findEventByIdAndInitiator_Id(eventId, userId));
    }

    @Override
    public EventFullDto changeEventsByUser(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = repository.findById(eventId).orElseThrow();

        if (event.getState().equals(EventStatus.PENDING) || event.getState().equals(EventStatus.CANCELED)) {
            if (updateEventUserRequest.getAnnotation() != null) {
                event.setAnnotation(updateEventUserRequest.getAnnotation());
            }
            if (updateEventUserRequest.getCategory() != null) {
                event.setCategory(mapToCategory(updateEventUserRequest.getCategory()));
            }
            if (updateEventUserRequest.getDescription() != null) {
                event.setDescription(updateEventUserRequest.getDescription());
            }
            if (updateEventUserRequest.getEventDate() != null) {
                event.setEventDate(LocalDateTime.parse(updateEventUserRequest.getEventDate()));
            }
            if (updateEventUserRequest.getLocation() != null) {
                event.setLocation(updateEventUserRequest.getLocation());
            }
            if (updateEventUserRequest.getPaid() != null) {
                event.setPaid(updateEventUserRequest.getPaid());
            }
            if (updateEventUserRequest.getParticipantLimit() != null) {
                event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
            }
            if (updateEventUserRequest.getRequestModeration() != null) {
                event.setRequestModeration(updateEventUserRequest.getRequestModeration());
            }
            if (updateEventUserRequest.getStateAction() != null) {
                switch (updateEventUserRequest.getStateAction()) {
                    case SEND_TO_REVIEW:
                        event.setState(EventStatus.PUBLISHED);
                        break;
                    case CANCEL_REVIEW:
                        event.setState(EventStatus.CANCELED);
                        break;
                }
            }
            if (updateEventUserRequest.getTitle() != null) {
                event.setTitle(updateEventUserRequest.getTitle());
            }

            return mapToEventFullDto(repository.save(event));
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Event getEventById(int eventId) {
        return repository.findById(eventId).orElseThrow();
    }

    private Pageable paged(Integer from, Integer size) {
        Pageable page;
        if (from != null && size != null) {
            if (from < 0 || size < 0) {
                throw new RuntimeException();
            }
            page = PageRequest.of(from > 0 ? from / size : 0, size);
        } else {
            page = PageRequest.of(0, 4);
        }
        return page;
    }
}