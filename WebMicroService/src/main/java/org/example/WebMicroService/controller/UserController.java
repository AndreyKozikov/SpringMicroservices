package org.example.WebMicroService.controller;

import org.example.WebMicroService.model.UserAdd;
import org.example.WebMicroService.model.UserDTO;
import org.example.WebMicroService.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Контроллер для управления пользователями.
 * Обеспечивает функциональность для создания новых пользователей,
 * отображения формы добавления пользователя и просмотра списка всех пользователей.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Метод обрабатывает GET-запрос на отображение страницы управления пользователями
     *
     * @return возвращает ссылку на HTML страницу
     */
    @GetMapping("/managment")
    public String userManagment() {
        return "/users/users_managment";
    }

    /**
     * Метод обрабатывает GET-запрос и возвращает форму добавления пользователя
     *
     * @param user объект пользователя
     * @return имя шаблона для отображения
     */
    @GetMapping("/add")
    public String showUserForm(@ModelAttribute("user") UserDTO user) {
        //@ModelAttribute создает пустого пользователя и передает в шаблон для связывания параметров шаблона и класса
        return "/users/add_user";
    }

    /**
     * Метод обрабатывает POST-запрос на добавление нового пользователя в базу
     *
     * @param user  объект пользователя
     * @param model модель для передачи данных в представление
     * @return имя шаблона для отображения
     */
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") UserAdd user, Model model) {
        userService.addUser(user);
        return "/users/users_managment";
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam("query") String name) {
        List<UserDTO> users = userService.searchUser(name);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get_all")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Метод обрабатывает GET-запрос на отображение формы редактирования пользователя.
     *
     * @param id    идентификатор пользователя для редактирования.
     * @param model модель для передачи данных на страницу.
     * @return имя представления для редактирования пользователя.
     */
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        UserDTO user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/users/user_edit";
    }

    @PatchMapping("/edit/{id}")
    @ResponseBody //Ответ автоматически преобразуется в JSON
    public Map<String, String> editUser(@RequestBody UserDTO user,
                                        @PathVariable("id") long id) {

        userService.updateUser(id, user);
        Map<String, String> response = new HashMap<>();
        response.put("location", "/users/managment");
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "/users/users_managment";
    }

    // Метод для проверки валидности пользователя
    private boolean isUserInvalid(UserDTO user) {
        return user == null ||
                user.getUserName() == null || user.getUserName().isEmpty() ||
                user.getEmail() == null || user.getEmail().isEmpty();
    }

}
