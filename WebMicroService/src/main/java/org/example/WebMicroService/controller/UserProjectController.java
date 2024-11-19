package org.example.WebMicroService.controller;


import org.example.WebMicroService.model.Project;
import org.example.WebMicroService.model.UserDTO;
import org.example.WebMicroService.model.UserProjectRequest;
import org.example.WebMicroService.service.ProjectService;
import org.example.WebMicroService.service.UserProjectService;
import org.example.WebMicroService.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Контроллер для управления связями между пользователями и проектами.
 * Обеспечивает функциональность для просмотра пользователей проекта и проектов пользователя,
 * добавления пользователей в проект и удаления пользователей из проекта.
 */
@Controller
@AllArgsConstructor
public class UserProjectController {

    private final UserProjectService userProjectService;
    private final UserService userService;
    private final ProjectService projectService;

    /**
     * Метод обрабатывает GET-запрос на получение пользователей по идентификатору проекта
     *
     * @param projectId идентификатор проекта
     * @param model     модель для передачи данных в представление
     * @return имя шаблона с пользователями проекта
     */
    @GetMapping("/project/{id}/users")
    public String getUsersByProjectId(@PathVariable("id") Long projectId, Model model) {
        List<UserDTO> users = userProjectService.getUsersByProjectId(projectId);
        if (users != null) {
            model.addAttribute("projectId", projectId);
            model.addAttribute("users", users);
            return "/projects/project_users";
        }
        return "redirect:/projects/project_managment";
    }

    /**
     * Метод обрабатывает GET-запрос на получение проектов по идентификатору пользователя
     *
     * @param userId идентификатор пользователя
     * @param model  модель для передачи данных в представление
     * @return имя шаблона с проектами пользователя
     */
    @GetMapping("/user/{id}/projects")
    public String getProjectsByUserId(@PathVariable("id") Long userId, Model model) {
        List<Project> projects = userProjectService.getProjectsByUserId(userId);
        if (projects != null) {
            model.addAttribute("userName", userService.getUserById(userId).getUserName());
            model.addAttribute("projects", projects);
            return "/users/user_projects";
        }
        return "redirect:/user_managment";
    }

    /**
     * Метод обрабатывает GET-запрос и возвращает форму для добавления пользователей к проекту
     *
     * @param projectId идентификатор проекта
     * @param model     модель для передачи данных в представление
     * @return имя шаблона для действий с проектом
     */
    @GetMapping("/add_user_to_project/{id}")
    public String addUserToProject(@PathVariable("id") Long projectId, Model model) {
        List<UserDTO> users = userProjectService.getUsersNotInProject(projectId);
        if (users != null) {
            model.addAttribute("projectId", projectId);
            model.addAttribute("projectName", projectService.findProjectById(projectId).getName());
            model.addAttribute("users", users);
            model.addAttribute("message", "Добавить выбранных пользователей");
            model.addAttribute("action", "add");
            return "/projects/add_remove_user_to_project";
        }
        return "redirect:/user";
    }

    /**
     * Метод обрабатывает POST-запрос на добавление выбранных пользователей к проекту
     *
     * @param projectId идентификатор проекта
     * @param userIds   список идентификаторов пользователей
     * @return имя шаблона с пользователями проекта после добавления
     */
    @PostMapping("/add_selected_users")
    public String addUserToProject(@RequestParam(value = "projectId", defaultValue = "-1") Long projectId,
                                   @RequestParam(value = "selectedUsers", defaultValue = "")
                                   List<Long> userIds) {

        if (projectId == -1 || userIds == null || userIds.isEmpty()) {
            return "redirect:/projects/project_managment"; // Перенаправление на главную страницу, если проект не найден
        }
        userProjectService.addUserToProject(projectId, userIds);
        return "/projects/project_managment";
    }

    /**
     * Метод обрабатывает GET-запрос и возвращает форму для удаления пользователей из проекта
     *
     * @param projectId идентификатор проекта
     * @param model     модель для передачи данных в представление
     * @return имя шаблона для действий с проектом
     */
    @GetMapping("/remove_users_from_project/{id}")
    public String removeUserFromProject(@PathVariable("id") Long projectId, Model model) {
        List<UserDTO> users = userProjectService.getUsersByProjectId(projectId);
        if (users != null) {
            model.addAttribute("projectId", projectId);
            model.addAttribute("projectName", projectService.findProjectById(projectId).getName());
            model.addAttribute("users", users);
            model.addAttribute("message", "Удалить выбранных пользователей");
            model.addAttribute("action", "remove");
            return "/projects/add_remove_user_to_project";
        }
        return "redirect:/projects/project_managment";
    }

    /**
     * Метод обрабатывает POST-запрос на удаление выбранных пользователей из проекта
     *
     * @return имя шаблона с пользователями проекта после удаления
     */
    @PostMapping("/remove_selected_users")
    public String removeUserFromProject(@RequestParam Long projectId,
                                        @RequestParam(required = false) List<Long> selectedUsers) {
        UserProjectRequest userProjectRequest = new UserProjectRequest();
        userProjectRequest.setProjectId(projectId);
        userProjectRequest.setUserIds(selectedUsers);
        userProjectService.removeUserFromProject(userProjectRequest);
        return "redirect:/projects/managment";
    }
}
