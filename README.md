# java-explore-with-me

## Описание приложения

Бекэнд сервиса-афиши, в котором можно предложить какое-либо событие.
Приложение позволит пользователям делиться информацией об интересных событиях и находить компанию для участия в них.

RESTful API имеет многомодульную архитектуру: основной модуль с главной частью бизнес-логики,
модуль сервиса статистики, который в свою очередь делится на подмодули
(подмодуль с клиенской частью для связи с основным модулем, подмодуль с общими dto-объектами, подмодуль с бизнес-логикой
статистической части).

## Примеры запросов

Сервис имеет 3 уровня доступа: public (для неавторизованных пользователей), private (для авторизованных пользователей),
admin (для админитраторов проекта).
В связи с этим примеры запросов будут разделены на 3 категории.

Для примера адрес будет взят из актуальных тестов Postman.

Public:

1. Получение подборок событий: GET http://localhost:8080/compilations
2. Получение подборок событий по id: GET http://localhost:8080/compilations/{compId}
3. Получение категорий: GET http://localhost:8080/categories
4. Получение категорий по id: GET http://localhost:8080/categories/{catId}
5. Получение событий с возможностью фильтрации: GET http://localhost:8080/events
6. Получение подробной информации об опубликованном событии по его id: GET http://localhost:8080/events/{id}

User:

1. Получение событий, добавленных текущим пользователем: GET http://localhost:8080/users/{userId}/events
2. Добавление нового события: POST http://localhost:8080/users/{userId}/events
3. Получение полной информации о событии, добавленном текущим пользователем:
   GET http://localhost:8080/users/{userId}/events/{eventId}
4. Изменение события, добавленном текущим пользователем: PATCH http://localhost:8080/users/{userId}/events/{eventId}
5. Получение информации о запросах на участие в событии текущего пользователя:
   GET http://localhost:8080/users/{userId}/events/{eventId}/requests
6. Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя:
   PATCH http://localhost:8080/users/{userId}/events/{eventId}/requests
7. Получение информации о заявках текущего пользователя на участие в событиях:
   GET http://localhost:8080/users/{userId}/requests
8. Добавление запроса от текущего пользователя на участие в событии: POST http://localhost:8080/users/{userId}/requests
9. Отмена своего запроса на участие в событии: POST http://localhost:8080/users/{userId}/requests/{requestId}/cancel
10. Подписка на пользователя: POST http://localhost:8080/users/{userId}/subscribe/{userId}
11. Отмена подписки на пользователя: DELETE http://localhost:8080/users/{userId}/unsubscribe/{userId}
12. Просмотр событий пользователя из списка подписок по id:
    GET http://localhost:8080/users/{userId}/subscription/{userId}/events
13. Просмотр событий подписчика по id: GET http://localhost:8080/users/{userId}/subscriber/{userId}/events
14. Просмотр событий всех пользователей из подписок: GET http://localhost:8080/users/{userId}/subscription/events
15. Просмотр событий всех подписчиков: GET http://localhost:8080/users/{userId}/subscriber/events

Admin:

1. Добавление новой категории: POST http://localhost:8080/admin/categories
2. Удаление новой категории: DELETE http://localhost:8080/admin/categories/{catId}
3. Изменение категории: PATCH http://localhost:8080/admin/categories/{catId}
4. Поиск события: GET http://localhost:8080/admin/events
5. Редактирование данных события и его статуса (отклонение/публикация):
   PATCH http://localhost:8080/admin/events/{eventId}
6. Получение информации о пользователях: GET http://localhost:8080/admin/users
7. Добавление нового пользователя: POST http://localhost:8080/admin/users
8. Удаление пользователя: DELETE http://localhost:8080/admin/users/{userId}
9. Добавление новой подборки: POST http://localhost:8080/admin/compilations
10. Удаление подборки: DELETE http://localhost:8080/admin/compilations/{compId}
11. Обновить информацию о подборке: PATCH http://localhost:8080/admin/compilations/{compId}

## Пример взаимодействия с БД
ER diagram

Main:
![SCHEME](https://raw.githubusercontent.com/pankkovv/java-explore-with-me/main/ER%20main%20diagram.bmp)

Stats:
![SCHEME](https://raw.githubusercontent.com/pankkovv/java-explore-with-me/main/ER%20stats%20diagram.bmp)

Пример обращения к БД:
```Data JPA
Page<Event> findEventsByInitiator_Id(int userId, Pageable page);

Optional<Event> findEventByIdAndInitiator_Id(int eventId, int userId);

Optional<Event> findEventsByIdAndStateIs(int eventId, EventStatus status);
```
----

## Приложение написано на Java и протестировано слайсами на базе Postman. Пример кода:

```java
    @Override
public CategoryDto changeCategories(int catId,CategoryDto categoryDto){
        Category oldCategory=repository.findById(catId).orElseThrow();
        if(categoryDto.getName()!=null){
        oldCategory.setName(categoryDto.getName());
        }
        log.debug(LogMessages.ADMIN_PATCH_CATEGORIES_ID.label,catId);
        return mapToCategoryDto(repository.save(oldCategory));
        }
```

----

## Стек

- Java SE 9
- Spring Boot
- Spring Data JPA
- Hibernate
- Query DSL
- PostgreSQL
- Docker

## Шаги по запуску приложения

- Клонировать репозиторий
- Синхронизировать pom.xml каждого модуля с локальным репозиторием зависимостей
- Запустить билд через консоль с помощью команды (docker-compose up)
- Взаимодействовать с приложением через API http://localhost:8080/

----
Приложение создано в рамках прохождения курса Java-разработчик
от [Яндекс-Практикум](https://practicum.yandex.ru/java-developer/ "Тут учат Java!") 


