package ru.practicum.main.events.close.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.categories.model.Category;
import ru.practicum.main.events.dto.*;
import ru.practicum.main.events.enums.EventStatus;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.repository.EventsRepository;
import ru.practicum.main.locat.service.LocationService;
import ru.practicum.main.requests.close.service.CloseRequestsService;
import ru.practicum.main.requests.dto.ParticipationRequestDto;
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
    private CloseRequestsService requestsService;
    @Autowired
    private LocationService locationService;
    @Override
    public List<EventShortDto> getEventsByUser(int userId, int from, int size) {
        Pageable page = paged(from, size);
        return mapToListEventShortDto(repository.findEventsByInitiator_Id(userId, page));
    }

    @Override
    public EventFullDto createEvents(int userId, NewEventDto newEventDto) {
        User user = User.builder().id(1).name("Allen Feeney").email("Layne_Bednar@gmail.com").build();
        Category category = Category.builder().id(10).name("Fantastic0").build();
        locationService.save(newEventDto.getLocation());

        return mapToEventFullDto(repository.save(mapToEvent(newEventDto, category, user)));
    }

    @Override
    public EventFullDto getEventsByUserFullInfo(int userId, int eventId) {
        return mapToEventFullDto(repository.findEventByIdAndInitiator_Id(eventId, userId));
    }

    @Override
    public EventFullDto changeEventsByUser(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        User user = new User();
        Event event = repository.findById(eventId).orElseThrow();

        if(event.getState().equals(EventStatus.REJECT_EVENT) || event.getState().equals(EventStatus.PENDING)){
            if(updateEventUserRequest.getAnnotation() != null){
                event.setAnnotation(updateEventUserRequest.getAnnotation());
            }
            if(updateEventUserRequest.getCategory() != null){
                event.setCategory(mapToCategory(updateEventUserRequest.getCategory()));
            }
            if(updateEventUserRequest.getDescription() != null){
                event.setDescription(updateEventUserRequest.getDescription());
            }
            if(updateEventUserRequest.getEventDate() != null){
                event.setEventDate(LocalDateTime.parse(updateEventUserRequest.getEventDate()));
            }
            if(updateEventUserRequest.getLocation() != null){
                event.setLocation(updateEventUserRequest.getLocation());
            }
            if(updateEventUserRequest.isPaid()){
                event.setPaid(updateEventUserRequest.isPaid());
            }
            if(updateEventUserRequest.getParticipantLimit() != (Integer) null){
                event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
            }
            if(updateEventUserRequest.getStateAction() != null){
                event.setState(updateEventUserRequest.getStateAction());
            }
            if(updateEventUserRequest.getTitle() != null){
                event.setTitle(updateEventUserRequest.getTitle());
            }

            return mapToEventFullDto(repository.save(event));
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(int userId, int eventId) {
        return requestsService.getRequestsByUserOtherEvents(userId);
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusRequestsByUser(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        Event event = repository.findById(eventId).orElseThrow();
        return null;
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
