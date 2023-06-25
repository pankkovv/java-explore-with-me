package ru.practicum.main.compilations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.compilations.model.Compilation;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CompilationsRepository extends JpaRepository<Compilation, Integer> {
    List<Compilation> findByPinnedContaining(boolean pinned, Pageable page);
}
