package ru.practicum.main.locat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.locat.model.Location;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
