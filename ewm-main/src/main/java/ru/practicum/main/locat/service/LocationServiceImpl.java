package ru.practicum.main.locat.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.locat.model.Location;
import ru.practicum.main.locat.repository.LocationRepository;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository repository;
    @Override
    public Location save(Location location) {
        return repository.save(location);
    }
}
