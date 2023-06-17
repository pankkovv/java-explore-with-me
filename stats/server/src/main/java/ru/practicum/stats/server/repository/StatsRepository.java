package ru.practicum.stats.server.repository;

import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.server.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Stat, Long> {

    @Query(value = "select s " +
            "from stats as s " +
            "where s.timestamp between ?1 and ?2 and " +
            "lower(s.uri) ilike lower(?3) " +
            "group by s.ip", nativeQuery = true)
    List<Stat> findStatUriUnique(LocalDateTime start, LocalDateTime end, String uri);

    @Query(value = "select s " +
            "from stats as s " +
            "where s.timestamp between ?1 and ?2 " +
            "group by s.ip", nativeQuery = true)
    List<Stat> findStatUnique(LocalDateTime start, LocalDateTime end);

    @Query(value = "select s " +
            "from stats as s " +
            "where s.timestamp between ?1 and ?2 " +
            "group by s.ip", nativeQuery = true)
    List<Stat> findUriStat(LocalDateTime start, LocalDateTime end, String uri);

    @Query(value = "select s " +
            "from stats as s " +
            "where s.timestamp between ?1 and ?2 " +
            "group by s.ip", nativeQuery = true)
    List<Stat> findStat(LocalDateTime start, LocalDateTime end);
}
