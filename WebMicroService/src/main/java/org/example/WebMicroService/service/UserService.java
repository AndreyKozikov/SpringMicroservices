package org.example.WebMicroService.service;


import lombok.AllArgsConstructor;
import org.example.WebMicroService.client.UserClient;
import org.example.WebMicroService.model.UserAdd;
import org.example.WebMicroService.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Сервис для управления пользователями.
 * Предоставляет методы для работы с пользователями в системе.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserClient userClient;

//
//    /**
//     * Получает пользователя по его username
//     *
//     * @param username
//     * @return бъект пользователя или null, если пользователь не найден
//     */
//    Optional<UserDTO> findByUserName(String username) {
//        return userClient.findByUserName(username);
//    }

    /**
     * Добавляет нового пользователя в систему.
     *
     * @param userAdd объект пользователя для добавления
     */
    public void addUser(UserAdd userAdd) {
        userClient.addUser(userAdd);
    }

    public List<UserDTO> searchUser(String query) {
        ResponseEntity<List<UserDTO>> response = userClient.searchUser(query);
        return response.getBody();
    }

    /**
     * Получает список всех пользователей в системе.
     *
     * @return список всех пользователей
     */
    public List<UserDTO> getAllUsers() {
        ResponseEntity<List<UserDTO>> response = userClient.getAllUsers();
        return response.getBody();
    }

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return объект пользователя или null, если пользователь не найден
     */
    public UserDTO getUserById(Long userId) {
        ResponseEntity<UserDTO> response = userClient.findUserById(userId);
        return response.getBody();
    }


    /**
     * Обновляет данные пользователя по его идентификатору.
     * <p>
     * Этот метод извлекает пользователя по идентификатору,
     * обновляет его свойства и сохраняет изменения в репозитории.
     *
     * @param userId идентификатор пользователя, который необходимо обновить.
     * @param user   объект пользователя, содержащий обновленные данные.
     */
    public void updateUser(Long userId, UserDTO user) {
        UserAdd userToBeUpdated = new UserAdd(user.getUserName(), user.getEmail(), user.getRole());
        userClient.updateUserById(userToBeUpdated, userId);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     * <p>
     * Этот метод удаляет пользователя из репозитория по указанному идентификатору.
     *
     * @param userId идентификатор пользователя, которого необходимо удалить.
     */
    public ResponseEntity<?> deleteUser(Long userId) {
        return ResponseEntity.ok(userClient.deleteUserById(userId));
    }


}
