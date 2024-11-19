package org.example.WebMicroService.client;


import org.example.WebMicroService.model.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "UserProjectService", path = "/api/projects", contextId = "projectClient")
public interface ProjectClient {
    @PostMapping("/add")
    ResponseEntity<Project> addProject(@RequestBody Project project);

    @GetMapping("/search")
    ResponseEntity<List<Project>> searchProject(@RequestParam("query") String query);

    @GetMapping("/get_all")
    ResponseEntity<List<Project>> getAllProjects();

    @GetMapping("/find/{id}")
    ResponseEntity<Project> findProjectById(@PathVariable("id") Long id);

    @PostMapping("/edit/{id}")
    ResponseEntity<Project> updateProjectById(@PathVariable("id") Long id, @RequestBody Project project);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Project> deleteProjectById(@PathVariable("id") Long id);
}
