package ru.practicum.stats.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.server.model.Stat;

@Repository
public interface StatsRepository extends JpaRepository<Stat, Long> {
}
