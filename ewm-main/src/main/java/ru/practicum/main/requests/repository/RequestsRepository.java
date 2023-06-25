package ru.practicum.main.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.requests.model.ParticipationRequest;

import java.util.List;

@Repository
public interface RequestsRepository extends JpaRepository<ParticipationRequest, Integer> {

    List<ParticipationRequest> findParticipationRequestsByRequester_Id(int userId);

    ParticipationRequest findParticipationRequestByIdAndRequester_Id(int requestId, int userId);
}
