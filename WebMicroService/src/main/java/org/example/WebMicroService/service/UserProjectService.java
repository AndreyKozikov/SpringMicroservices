package org.example.WebMicroService.service;

import org.example.WebMicroService.client.UserProjectClient;
import org.example.WebMicroService.model.Project;
import org.example.WebMicroService.model.UserDTO;
import lombok.AllArgsConstructor;
import org.example.WebMicroService.model.UserProjectRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * Сервис для управления связи между пользователями и проектами.
 * Предоставляет методы для работы с пользователями в системе.
 */
@Service
@AllArgsConstructor
public class UserProjectService {

    private final UserProjectClient userProjectClient;
    private final UserService userService;
    private final ProjectService projectService;
    private ApplicationEventPublisher publisher;

    /**
     * Метод, возвращающий список пользователей, связанных с определенным проектом
     *
     * @param projectId идентификатор проекта
     * @return список пользователей
     */
    public List<UserDTO> getUsersByProjectId(Long projectId) {
        ResponseEntity<List<UserDTO>> response = userProjectClient.getUsersInProject(projectId);
        return response.getBody();
    }

    /**
     * Метод, возвращающий список проектов, связанных с определенным пользователем
     *
     * @param userId идентификатор пользователя
     * @return список проектов
     */
    public List<Project> getProjectsByUserId(Long userId) {
        ResponseEntity<List<Project>> response = userProjectClient.getProjectsByUserId(userId);
        return response.getBody();
    }

    /**
     * Метод, возвращающий список пользователей, не входящих в проект
     *
     * @param projectId идентификатор проекта
     * @return список пользователей, не входящих в проект
     */
    public List<UserDTO> getUsersNotInProject(Long projectId) {
        if (!projectValidation(projectId)){
            return null;
        }
        ResponseEntity<List<UserDTO>> response = userProjectClient.getUsersNotIProject(projectId);
        return response.getBody();
    }

    /**
     * Метод, добавляющий пользователя к проекту
     *
     * @param projectId идентификатор проекта
     * @param userIds   список идентификаторов пользователей
     * @return true, если хотя бы один пользователь был добавлен, иначе false
     */

    public void addUserToProject(Long projectId, List<Long> userIds) {
        if (!projectValidation(projectId)) {
            return;
        }
        UserProjectRequest request = new UserProjectRequest();
        request.setProjectId(projectId);
        request.setUserIds(userIds);
        userProjectClient.addUsersToProject(request);
    }

    /**
     * Метод, удаляющий пользователя из проекта
     *
     * @return true, если хотя бы один пользователь был удален, иначе false
     */
    public void removeUserFromProject(UserProjectRequest request) {
        userProjectClient.removeUsersFromProject(request);
    }


    /**
     * Метод проверяет, существует ли проект с заданным идентификатором.
     *
     * @param projectId идентификатор проекта
     * @return true, если проект существует; false в противном случае
     */
    private boolean projectValidation(Long projectId) {
        Project project = projectService.findProjectById(projectId);
        return project != null;
    }

    /**
     * Метод проверяет, существует ли пользователь с заданным идентификатором.
     *
     * @param userId идентификатор пользователя
     * @return true, если пользователь существует; false в противном случае
     */
    private boolean userValidation(Long userId) {
        UserDTO user = userService.getUserById(userId);
        return user != null;
    }
}
