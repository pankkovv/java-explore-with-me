package ru.practicum.main.events.admin.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.dto.UpdateEventAdminRequest;
import ru.practicum.main.events.model.Event;
import ru.practicum.main.events.repository.EventsRepository;
import ru.practicum.main.users.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main.categories.mapper.CatMap.mapToCategory;
import static ru.practicum.main.categories.mapper.CatMap.mapToCategoryDto;
import static ru.practicum.main.events.mapper.EventsMap.mapToEventFullDto;
import static ru.practicum.main.events.mapper.EventsMap.mapToListEventFullDto;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class AdminEventsServiceImpl implements AdminEventsService{
    @Autowired
    private EventsRepository repository;
    @Override
    public List<EventFullDto> findEvents(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart, String rangeEnd, int from, int size) {
        return mapToListEventFullDto(repository.findAll());
    }

    @Override
    public EventFullDto changeEvents(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = repository.findById(eventId).orElseThrow();

        if(updateEventAdminRequest.getAnnotation() != null){
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
        if(updateEventAdminRequest.getCategory() != null){
            event.setCategory(mapToCategory(updateEventAdminRequest.getCategory()));
        }
        if(updateEventAdminRequest.getDescription() != null){
            event.setDescription(updateEventAdminRequest.getDescription());
        }
        if(updateEventAdminRequest.getEventDate() != null){
            event.setEventDate(LocalDateTime.parse(updateEventAdminRequest.getEventDate()));
        }
        if(updateEventAdminRequest.getLocation() != null){
            event.setLocation(updateEventAdminRequest.getLocation());
        }
        if(updateEventAdminRequest.isPaid()){
            event.setPaid(updateEventAdminRequest.isPaid());
        }
        if(updateEventAdminRequest.getParticipantLimit() != (Integer) null){
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
        if(updateEventAdminRequest.isRequestModeration()){
            event.setRequestModeration(updateEventAdminRequest.isRequestModeration());
        }
        if(updateEventAdminRequest.getStateAction() != null){
            event.setState(updateEventAdminRequest.getStateAction());
        }
        if(updateEventAdminRequest.getTitle() != null){
            event.setTitle(updateEventAdminRequest.getTitle());
        }

        return mapToEventFullDto(repository.save(event));
    }
}
