package org.example.controller;

import org.example.repository.UsersProjectRepository;
import org.springframework.web.bind.annotation.RestController;
import org.example.service.UserProjectService;
import org.example.service.UserService;
import lombok.AllArgsConstructor;
import org.example.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Контроллер для управления пользователями через API.
 * Предоставляет RESTful интерфейс для операций над пользователями.
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserProjectService userProjectService;
    private final UsersProjectRepository usersProjectRepository;

    /**
     * Метод обрабатывает GET-запрос на получение списока всех пользователей.
     *
     * @return список всех пользователей в виде ответа HTTP.
     */

    @GetMapping("/get_all")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Метод обрабатывает GET-запрос на поиск пользователей по имени или адресу электронной почты.
     *
     * @param query текст для поиска в именах пользователей или адресах электронной почты.
     * @return список пользователей, соответствующих запросу.
     */

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("query") String query) {
        List<User> users = userService.findUserByUserNameOrByEmail(query);
        return ResponseEntity.ok(users);
    }

    /**
     * Метод обрабатывает PATCH-запрос на обновление информации о пользователе по идентификатору.
     *
     * @param user объект пользователя с новыми данными.
     * @param id   идентификатор пользователя для обновления.
     * @return ответ с местоположением после успешного обновления.
     */

    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editUser(@RequestBody User user, @PathVariable("id") long id) {
        userService.updateUser(id, user);
        return ResponseEntity.ok(user);
    }

    /**
     * Метод обрабатывает DELETE-запрос на удаление пользователя по идентификатору.
     *
     * @param userId идентификатор пользователя для удаления.
     * @return ответ с местоположением после успешного удаления.
     */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(userId);
    }

    /**
     * Метод обрабатывает POST запрос на добавление нового пользователя в базу
     *
     * @return статус обработки запроса сервером
     */

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User userAdd) {
        User user = userService.addUser(userAdd);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}

