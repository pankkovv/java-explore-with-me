package ru.practicum.main.compilations.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.compilations.model.Compilations;

import java.util.List;

@Repository
public interface CompilationsRepository extends JpaRepository<Compilations, Integer> {
    List<Compilations> findCompilationsByPinnedIs(boolean pinned, Pageable page);
}