package org.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;


/**
 * Сущность, представляющая пользователя в системе.
 * Содержит основную информацию о пользователе, включая имя пользователя, пароль, email и роль.
 */
@Entity
@Data
@Table(name="users")
public class User{

    /**
     * Сущность, представляющая пользователя в системе.
     * Содержит основную информацию о пользователе, включая имя пользователя, пароль, email и роль.
     */
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     * Должно быть уникальным и не может быть null.
     * Максимальная длина - 100 символов.
     */
    @Column(name="user_name", nullable = false, length = 100)
    private String userName;

    /**
     * Электронная почта пользователя.
     * Может быть null.
     */
    @Column
    private String email;

    @Column(name="role")
    private String role;

//    cascade = CascadeType.ALL: указывает Hibernate автоматически применять все каскадные операции
//    к зависимым записям UsersProject, включая удаление.
//    orphanRemoval = true: придает дополнительный эффект, удаляя "осиротевшие" записи,
//    т.е. записи UsersProject, которые больше не связаны с User.

    /**
     * Список проектов, связанных с данным пользователем.
     *
     * Это поле представляет собой связь "один ко многим",
     * где один пользователь может быть связан с несколькими проектами через
     * сущность {@link UsersProject}.
     *
     * @see UsersProject
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UsersProject> usersProjects = new ArrayList<>();

}
