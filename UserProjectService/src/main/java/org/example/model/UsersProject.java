package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;


/**
 * Сущность, представляющая связь между пользователями и проектами.
 * Реализует отношение многие-ко-многим между пользователями и проектами.
 * Наследуется от EntityWithRelation, добавляя специфические поля для связи.
 */
@Entity
@Data
@Table(name = "users_project")
public class UsersProject {

    /**
     * Уникальный идентификатор сущности.
     * Генерируется автоматически при сохранении в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Автоинкремент
    private Long id;

    /**
     * Связь с проектом.
     * Представляет проект, к которому привязан пользователь.
     * Реализует отношение многие-к-одному с сущностью Project.
     */
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @JsonBackReference // Разрыв циклической зависимости на стороне Project
    private Project project;

    /**
     * Связь с пользователем.
     * Представляет пользователя, который привязан к проекту.
     * Реализует отношение многие-к-одному с сущностью User.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference // Разрыв циклической зависимости на стороне User
    private User user;
}
