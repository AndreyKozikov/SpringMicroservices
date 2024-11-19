package org.example.WebMicroService.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainEndPoints {

//    private final UserService userService;
//    private final UserProjectService userProjectService;


    /**
     * Эндпоинт для отображения страницы входа.
     *
     * @return Название представления страницы входа.
     */
    @GetMapping("/admin/managment")
    public String adminPanel() {
        return "mainpage";
    }

//    /**
//     * Эндпоинт для отображения профиля пользователя с проектами.
//     *
//     * @param model Модель для передачи данных в представление.
//     * @param request HTTP запрос для извлечения данных пользователя из токена.
//     * @return Название представления страницы профиля пользователя.
//     */
//    @GetMapping("/user/profile")
//    public String userProfile(Model model, HttpServletRequest request) {
//        User user = userService.getUserFromToken(request);
//        model.addAttribute("user", user);
//        List<Project> projects = userProjectService.getProjectsByUserId(user.getId());
//        model.addAttribute("projects", projects);
//        return "/user/profile";
//    }
}
