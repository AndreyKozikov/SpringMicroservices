package org.example.WebMicroService.client;


import org.example.WebMicroService.model.Project;
import org.example.WebMicroService.model.UserDTO;
import org.example.WebMicroService.model.UserProjectRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "UserProjectService", path = "/api", contextId = "userProjectClient")
public interface UserProjectClient {

    @GetMapping("/users_in_project")
    ResponseEntity<List<UserDTO>> getUsersInProject(@RequestParam("projectId") Long projectId);

    @GetMapping("/user_projects")
    ResponseEntity<List<Project>> getProjectsByUserId(@RequestParam("userId") Long userId);

    @PostMapping("/add_users_to_project")
    ResponseEntity<String> addUsersToProject(@RequestBody UserProjectRequest request);

    @DeleteMapping("/remove_users_from_project")
    ResponseEntity<String> removeUsersFromProject(UserProjectRequest request);


    @GetMapping("/users_not_in_project/{id}")
    ResponseEntity<List<UserDTO>> getUsersNotIProject(@PathVariable("id") Long projectId);
}
