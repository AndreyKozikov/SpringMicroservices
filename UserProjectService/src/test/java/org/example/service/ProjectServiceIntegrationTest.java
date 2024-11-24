package org.example.service;


import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ProjectServiceIntegrationTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    private Project createTestProject(String name, String description) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setCreatedDate(LocalDate.of(2024, 11, 24));
        return project;
    }

    @Test
    @Transactional
    void addProjectIntegrationTest() {
        // Создаем новый проект для добавления
        Project projectToAdd = createTestProject("Test Created",
                "Description to added test project");

        // Вызываем метод addProject сервиса для добавления проекта
        Project addedProject = projectService.addProject(projectToAdd);

        // Получаем из репозитория проект по ID проекта, который вернул сервис после добавления
        Project resultProject = projectRepository.findById(addedProject.getId()).orElseThrow();

        // Проверяем, что проект, записанный в базу данных, соответствует созданному проекту
        assertNotNull(addedProject.getId());
        assertEquals(projectToAdd.getName(), resultProject.getName());
        assertEquals(projectToAdd.getDescription(), resultProject.getDescription());
        assertEquals(projectToAdd.getCreatedDate(), resultProject.getCreatedDate());
    }

    @Test
    @Transactional
    void getAllProjectIntegrationTest() {
        // Очищаем базу данных перед тестом
        projectRepository.deleteAll();
        // Запрашиваем список всех проектов
        List<Project> projects = projectService.getAllProjects();
        // Убеждаемся, что после удаления в базе не осталось ни одного проекта
        assertEquals(0, projects.size());

        // Добавляем в базу 10 новых проектов
        for (int i = 0; i < 10; i++) {
            Project projectToAdd = createTestProject("Test Created " + i,
                    "Description to added test project " + i);
            projectRepository.save(projectToAdd);
        }

        // Получаем список добавленных проектов
        List<Project> projectsAfterAdded = projectService.getAllProjects();

        //Проверяем, что количество проектов соответствует количеству добавленных проектов
        assertNotNull(projectsAfterAdded);
        assertEquals(10, projectsAfterAdded.size());

        // Дополнительные проверки для каждого проекта
        int i = 0;
        for (Project project : projectsAfterAdded) {
            assertEquals("Test Created " + i, project.getName());
            assertEquals("Description to added test project " + i, project.getDescription());
            assertEquals(LocalDate.of(2024, 11, 24), project.getCreatedDate());
            i++;
        }
    }

    @Test
    @Transactional
    void getProjectByIdIntegrationTest() {
        // Создаем новый проект
        Project projectToAdd = createTestProject("Test Created",
                "Description to added test project");

        // Вызываем метод save репозитория для добавления проекта
        Project addedProject = projectRepository.save(projectToAdd);

        // Получаем проект по ID проекта, который вернул репозиторий после добавления
        Project resultProject = projectService.findProjectById(addedProject.getId());

        // Проверяем, что проект, записанный в базу данных, соответствует созданному проекту
        assertNotNull(addedProject.getId());
        assertEquals(projectToAdd.getName(), resultProject.getName());
        assertEquals(projectToAdd.getDescription(), resultProject.getDescription());
        assertEquals(projectToAdd.getCreatedDate(), resultProject.getCreatedDate());

    }

    @Test
    @Transactional
    void findProjectByNameOrDescriptionIntegrationTest() {
        // Удаляем все проекты перед тестом, чтобы тест был независим от других данных
        projectRepository.deleteAll();

        // Создаем новый проект для добавления
        Project projectToAdd = createTestProject("Test project to find test",
                "Description to find test project");
        Project projectToAdd2 = createTestProject("Test project",
                "Description to find test project");

        // Вызываем метод addProject сервиса для добавления проекта
        projectService.addProject(projectToAdd);
        projectService.addProject(projectToAdd2);

       // Вызываем метод поиска проекта по имени или описанию
        List<Project> findProject = projectService.findProjectByNameOrDescription("find test");

        // Выполняем проверки, что проекты найдены и что в их полях есть искомое выражение
        assertEquals(2, findProject.size());

        for (Project project : findProject) {
            boolean containsInName = project.getName().contains("find test");
            boolean containsInDescription = project.getDescription().contains("find test");
            assertTrue(containsInName || containsInDescription, "find test");
        }
    }

    @Test
    @Transactional
    void updateProjectByIdIntegrationTest() {
        // Создаем новый проект с обновленными данными
        Long projectId = 1L;
        Project updatedProject = createTestProject("Test Updated", "Test Description");

        // Вызываем метод updateProjectById сервиса для обновления существующего проекта
        projectService.updateProjectById(projectId, updatedProject);

        // Считываем обновленный проект и проверяем, что данные обновились
        Project resultProject = projectRepository.findById(projectId).orElseThrow();
        assertEquals(updatedProject.getName(), resultProject.getName());
        assertEquals(updatedProject.getDescription(), resultProject.getDescription());
        assertEquals(updatedProject.getCreatedDate(), resultProject.getCreatedDate());
    }

    @Test
    @Transactional
    void deleteProjectByIdIntegrationTest() {
        Long projectId = 1L;
        // Проверяем что проект существует до удаления
        Project deletedProject = projectRepository.findById(projectId).orElseThrow();

        //Вызываем метод удаления
        projectService.deleteProjectById(projectId);

        //Проверяем, что проект больше не существует в базе данных
        Project resultProject = projectRepository.findById(projectId).orElse(null);
        assertNull(resultProject, "Проект не был удален");
    }
}
