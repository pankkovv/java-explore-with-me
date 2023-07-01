package ru.practicum.main.users.admin.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.messages.LogMessages;
import ru.practicum.main.users.dto.NewUserRequest;
import ru.practicum.main.users.dto.UserDto;
import ru.practicum.main.users.model.User;
import ru.practicum.main.users.repository.UsersRepository;

import java.util.List;

import static ru.practicum.main.users.mapper.UserMap.*;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class AdminUsersServiceImpl implements AdminUsersService {
    @Autowired
    private UsersRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getUsers(List<Integer> ids, int from, int size) {
        Pageable page = paged(from, size);
        if (ids != null && !ids.isEmpty()) {
            log.debug(LogMessages.ADMIN_GET_USERS.label);
            return mapToListUserDto(repository.findAllById(ids));
        } else {
            log.debug(LogMessages.ADMIN_GET_USERS.label);
            return mapToListUserDto(repository.findAll(page));
        }
    }

    @Override
    public UserDto createUsers(NewUserRequest newUserRequest) {
        log.debug(LogMessages.ADMIN_POST_USERS.label);
        return mapToUserDto(repository.save(mapToUser(newUserRequest)));
    }

    @Override
    public void deleteUsers(int userId) {
        repository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_USERS_EXCEPTION.label));

        log.debug(LogMessages.ADMIN_DELETE_USERS.label, userId);
        repository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(int userId) {
        return repository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_USERS_EXCEPTION.label));
    }

    private Pageable paged(Integer from, Integer size) {
        Pageable page;
        if (from != null && size != null) {
            if (from < 0 || size < 0) {
                throw new RuntimeException();
            }
            page = PageRequest.of(from > 0 ? from / size : 0, size);
        } else {
            page = PageRequest.of(0, 4);
        }
        return page;
    }
}
