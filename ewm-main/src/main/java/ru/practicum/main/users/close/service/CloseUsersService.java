package ru.practicum.main.users.close.service;

import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.users.dto.UserDto;

import java.util.List;

public interface CloseUsersService {
    UserDto subscribe(int userId, int subsId);

    UserDto unsubscribe(int userId, int subsId);

    List<EventShortDto> subscriptionEvents(int userId, int from, int size);

    List<EventShortDto> subscriptionByIdEvents(int userId, int subsId, int from, int size);

    List<EventShortDto> subscriberEvents(int userId, int from, int size);

    List<EventShortDto> subscriberByIdEvents(int userId, int subsId, int from, int size);
}
