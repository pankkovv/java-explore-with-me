package ru.practicum.main.compilations.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import ru.practicum.main.events.model.Event;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean pinned;
    private String title;
    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "events_id"),
            inverseJoinColumns = @JoinColumn(name = "compilations_id"))
    private List<Event> events;
}