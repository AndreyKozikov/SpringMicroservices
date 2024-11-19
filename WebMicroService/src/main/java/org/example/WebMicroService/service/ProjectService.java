package org.example.WebMicroService.service;

import org.example.WebMicroService.model.Project;

import lombok.AllArgsConstructor;
import org.example.WebMicroService.client.ProjectClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления проектами.
 * Предоставляет методы для работы с пользователями в системе.
 */
@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectClient projectClient;

    /**
     * Метод для добавления нового проекта
     *
     * @param project проект для добавления
     */
    public void addProject(Project project) {
        projectClient.addProject(project);
    }

    /**
     * Метод для получения всех проектов
     *
     * @return список всех проектов
     */
    public List<Project> getAllProjects() {
        ResponseEntity<List<Project>> response = projectClient.getAllProjects();
        return response.getBody();
    }

    /**
     * Метод для поиска проекта по идентификатору
     *
     * @param projectId идентификатор проекта
     * @return проект или выбрасывает исключение, если не найден
     */
    public Project findProjectById(Long projectId) {
        ResponseEntity<Project> response = projectClient.findProjectById(projectId);
        return response.getBody();
    }

    public List<Project> searchProjectByQuery(String searchString) {
        ResponseEntity<List<Project>> response = projectClient.searchProject(searchString);
        return response.getBody();
    }

    /**
     * Обновляет данные проекта по его идентификатору.
     * <p>
     * Этот метод извлекает проект по идентификатору,
     * обновляет его свойства и сохраняет изменения в репозитории.
     *
     * @param projectId идентификатор проекта, который необходимо обновить.
     * @param project   объект проекта, содержащий обновленные данные.
     */
    public void updateProjectById(Long projectId, Project project) {
        projectClient.updateProjectById(projectId, project);
    }

    /**
     * Удаляет проект по его идентификатору.
     *
     * @param projectId Идентификатор проекта, который нужно удалить.
     */
    public void deleteProjectById(Long projectId) {
        projectClient.deleteProjectById(projectId);
    }
}
