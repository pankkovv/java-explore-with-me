package ru.practicum.main.users.close.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.events.close.service.CloseEventsService;
import ru.practicum.main.events.dto.EventShortDto;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.messages.ExceptionMessages;
import ru.practicum.main.messages.LogMessages;
import ru.practicum.main.users.dto.UserDto;
import ru.practicum.main.users.model.User;
import ru.practicum.main.users.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.main.users.mapper.UserMap.mapToUserDto;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class CloseUsersServiceImpl implements CloseUsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CloseEventsService eventsService;

    @Override
    public UserDto subscribe(int userId, int subsId) {

        if (userId == subsId) {
            throw new ConflictException(ExceptionMessages.NOT_SUBSCRIBE_YOURSELF_EXCEPTION.label);
        }

        User user = findUsers(userId);
        User subscription = findUsers(subsId);

        if (subscription.isSubscriptionPermission()) {
            if (!user.getSubscriptions().contains(subscription)) {
                subscription.getSubscribers().add(user);
                user.getSubscriptions().add(subscription);
                usersRepository.save(subscription);

                log.debug(LogMessages.PRIVATE_POST_SUBSCRIPTIONS.label);
                return mapToUserDto(usersRepository.save(user));
            } else {
                throw new ConflictException(ExceptionMessages.NOT_SUBSCRIPTIONS_EXCEPTION.label);
            }
        } else {
            throw new ConflictException(ExceptionMessages.NOT_SUBSCRIPTIONS_UNRESOLVED_EXCEPTION.label);
        }
    }

    @Override
    public UserDto unsubscribe(int userId, int subsId) {

        if (userId == subsId) {
            throw new ConflictException(ExceptionMessages.NOT_UNSUBSCRIBE_YOURSELF_EXCEPTION.label);
        }

        User user = findUsers(userId);
        User subscription = findUsers(subsId);

        boolean conditionOne = user.getSubscriptions() != null && !user.getSubscriptions().isEmpty();
        boolean conditionTwo = subscription.getSubscribers() != null && !subscription.getSubscribers().isEmpty();

        if (conditionOne && conditionTwo) {
            if (user.getSubscriptions().contains(subscription)) {
                user.getSubscriptions().remove(subscription);
                subscription.getSubscribers().remove(user);
                usersRepository.save(subscription);

                log.debug(LogMessages.PRIVATE_DELETE_SUBSCRIPTIONS.label);
                return mapToUserDto(usersRepository.save(user));
            } else {
                throw new ConflictException(ExceptionMessages.NOT_SUBSCRIPTIONS_EXCEPTION.label);
            }
        } else {
            throw new ConflictException(ExceptionMessages.CONFLICT_EXCEPTION.label);
        }
    }

    @Override
    public List<EventShortDto> subscriptionEvents(int userId, int from, int size) {
        User user = findUsers(userId);
        List<EventShortDto> subscriptionsList = new ArrayList<>();

        if (!user.getSubscriptions().isEmpty()) {
            for (User subscription : user.getSubscriptions()) {
                subscriptionsList.addAll(eventsService.getEventsByUser(subscription.getId(), from, size));
            }
        } else {
            throw new ConflictException(ExceptionMessages.NOT_SUBSCRIPTIONS_EMPTY_EXCEPTION.label);
        }

        log.debug(LogMessages.PRIVATE_GET_SUBSCRIPTIONS.label);
        return subscriptionsList;
    }

    @Override
    public List<EventShortDto> subscriptionByIdEvents(int userId, int subsId, int from, int size) {
        User user = findUsers(userId);
        User subscription = findUsers(subsId);

        if (!user.getSubscriptions().isEmpty()) {
            if (user.getSubscriptions().contains(subscription)) {

                log.debug(LogMessages.PRIVATE_GET_SUBSCRIPTIONS_ID.label);
                return eventsService.getEventsByUser(subsId, from, size);
            } else {
                throw new ConflictException(ExceptionMessages.NOT_SUBSCRIPTIONS_EXCEPTION.label);
            }
        } else {
            throw new ConflictException(ExceptionMessages.NOT_SUBSCRIPTIONS_EMPTY_EXCEPTION.label);
        }
    }

    @Override
    public List<EventShortDto> subscriberEvents(int userId, int from, int size) {
        User user = findUsers(userId);
        List<EventShortDto> subscribersList = new ArrayList<>();

        if (!user.getSubscribers().isEmpty()) {
            for (User subscriber : user.getSubscribers()) {
                subscribersList.addAll(eventsService.getEventsByUser(subscriber.getId(), from, size));
            }
        } else {
            throw new ConflictException(ExceptionMessages.NOT_SUBSCRIBER_EMPTY_EXCEPTION.label);
        }

        log.debug(LogMessages.PRIVATE_GET_SUBSCRIBERS.label);
        return subscribersList;
    }

    @Override
    public List<EventShortDto> subscriberByIdEvents(int userId, int subsId, int from, int size) {
        User user = findUsers(userId);
        User subscriber = findUsers(subsId);

        if (!user.getSubscribers().isEmpty()) {
            if (user.getSubscribers().contains(subscriber)) {

                log.debug(LogMessages.PRIVATE_GET_SUBSCRIBERS_ID.label);
                return eventsService.getEventsByUser(subsId, from, size);
            } else {
                throw new ConflictException(ExceptionMessages.NOT_SUBSCRIBER_EXCEPTION.label);
            }
        } else {
            throw new ConflictException(ExceptionMessages.NOT_SUBSCRIBER_EMPTY_EXCEPTION.label);
        }
    }

    private User findUsers(int userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionMessages.NOT_FOUND_USERS_EXCEPTION.label));
    }
}
