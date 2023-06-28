package ru.practicum.main.users.admin.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.users.dto.NewUserRequest;
import ru.practicum.main.users.dto.UserDto;
import ru.practicum.main.users.model.User;
import ru.practicum.main.users.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.users.mapper.UserMap.*;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class AdminUsersServiceImpl implements AdminUsersService {
    @Autowired
    private UsersRepository repository;

    @Override
    public List<UserDto> getUsers(List<Integer> ids, int from, int size) {
        Pageable page = paged(from, size);
        List<User> listUser = new ArrayList<>();
        if (ids != null && !ids.isEmpty()) {
            for (Integer id : ids) {
                listUser.addAll(repository.findUsersById(id, page));
            }
            return mapToListUserDto(listUser);
        } else {
            return mapToListUserDto(repository.findAll(page));
        }
    }

    @Override
    public UserDto createUsers(NewUserRequest newUserRequest) {
        return mapToUserDto(repository.save(mapToUser(newUserRequest)));
    }

    @Override
    public void deleteUsers(int userId) {
        repository.deleteById(userId);
    }

    @Override
    public User getUserById(int userId) {
        return repository.findById(userId).orElseThrow();
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
