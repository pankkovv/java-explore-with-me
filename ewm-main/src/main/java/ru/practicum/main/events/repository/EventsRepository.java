package ru.practicum.main.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.events.model.Event;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event, Integer> {
    List<Event> findEventsByInitiator_Id(int userId, Pageable page);
    Event findEventByIdAndInitiator_Id(int eventId, int userId);
}
