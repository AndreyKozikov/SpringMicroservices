package org.example.WebMicroService.client;


import org.example.WebMicroService.model.UserAdd;
import org.example.WebMicroService.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "UserProjectService", path = "/api/users", contextId = "userClient")
public interface UserClient {

    @PostMapping("/add")
    ResponseEntity<UserDTO> addUser(@RequestBody UserAdd user);

    @GetMapping("/search")
    ResponseEntity<List<UserDTO>> searchUser(@RequestParam("query") String query);

    @GetMapping("/get_all")
    ResponseEntity<List<UserDTO>> getAllUsers();

    @GetMapping("/find/{id}")
    ResponseEntity<UserDTO> findUserById(@PathVariable("id") Long id);

    @PostMapping("/edit/{id}")
    ResponseEntity<UserDTO> updateUserById(@RequestBody UserAdd user, @PathVariable("id") Long id);

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteUserById(@PathVariable("id") Long id);
}
