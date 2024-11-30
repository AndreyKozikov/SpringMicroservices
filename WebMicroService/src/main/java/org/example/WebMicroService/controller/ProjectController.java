package org.example.WebMicroService.controller;

import org.example.WebMicroService.model.Project;
import org.example.WebMicroService.service.ProjectService;
import lombok.AllArgsConstructor;
import org.example.WebMicroService.service.WriteInfoFile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


/**
 * Контроллер для управления проектами.
 * Обеспечивает функциональность для создания новых проектов,
 * отображения формы добавления проекта и просмотра списка всех проектов.
 */
@Controller
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final WriteInfoFile writeInfoFile;
    private final ProjectService projectService;

    /**
     * Обрабатывает GET-запрос на отображение операций над проектами".
     *
     * @return имя представления для страницы управления проектами
     */
    @GetMapping("/managment")
    public String projectManagment() {
        return "/projects/project_managment";
    }

    /**
     * Метод обрабатывает GET-запрос и возвращает страницу для добавления нового проекта
     * @param project объект проекта
     * @return имя шаблона для отображения
     */
    @GetMapping("/add")
    public String showProjectForm(@ModelAttribute("project") Project project) {
        return "/projects/add_project";
    }

    /**
     * Метод обрабатывает POST-запрос на добавление нового проекта в базу данных.
     * Если проект не проходит проверку, осуществляется перенаправление на страницу добавления проекта.
     * В противном случае проект добавляется в базу, а информация о добавлении записывается в файл.
     *
     * @param project объект проекта, содержащий данные о новом проекте
     * @return имя шаблона для отображения: либо "/projects/add_project" в случае ошибки, либо "/projects/project_managment" после успешного добавления
     */
    @PostMapping("/add")
    public String addProject(@ModelAttribute("project") Project project){
        if (isProjectInvalid(project)){
            return "/projects/add_project";
        }
        projectService.addProject(project);
        writeInfoFile.writeToFile("output.log", "Добавлен проект " + project.toString());
        return "/projects/project_managment";
    }

    /**
     * Обрабатывает GET-запрос и отображает форму редактирования проекта.
     *
     * @param id идентификатор проекта для редактирования.
     * @param model модель для передачи данных на страницу.
     * @return имя представления для редактирования проекта.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Project projectToEdit = projectService.findProjectById(id);
        // Установим отформатированное значение даты, если необходимо
        // Форматируем дату в строку для отображения
        String formattedDate = projectToEdit.getCreatedDate() != null
                ? projectToEdit.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";
        model.addAttribute("formattedDate", formattedDate);
        model.addAttribute("project", projectToEdit);
        return "/projects/edit_project";
    }

    @PatchMapping("/edit/{id}")
    @ResponseBody //Ответ автоматически преобразуется в JSON
    public Map<String, String> updateProject(@RequestBody Project project, @PathVariable("id") Long id) {
        projectService.updateProjectById(id, project);

        // Возвращаем JSON с URL для перенаправления
        Map<String, String> response = new HashMap<>();
        response.put("location", "/projects/managment");
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id){
        projectService.deleteProjectById(id);
        return "/projects/managment";
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("query") String query, Model model) {
        return ResponseEntity.ok(projectService.searchProjectByQuery(query));
    }

    @GetMapping("/get_all")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    /**
     * Проверяет, является ли проект недействительным. Проект считается недействительным, если:
     * 1. Проект равен null.
     * 2. Имя проекта равно null или пустое.
     * 3. Описание проекта равно null или пустое.
     * 4. Дата создания проекта равна null.
     *
     * @param project Объект проекта, который проверяется.
     * @return true, если проект недействителен, иначе false.
     */
    private boolean isProjectInvalid(Project project) {
        return project == null ||
                project.getName() == null || project.getName().isEmpty() ||
                project.getDescription() == null || project.getDescription().isEmpty() ||
                project.getCreatedDate() == null;
    }
}
