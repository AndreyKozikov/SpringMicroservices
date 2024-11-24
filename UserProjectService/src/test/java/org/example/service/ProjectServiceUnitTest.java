package org.example.service;

import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)

public class ProjectServiceUnitTest {

    // Ставим заглушку
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void updateProjectByIdUnitTest() {
        Long projectId = 5L;
        //Настраиваем существующий в БД проект, который вернется при поиске по Id
        Project existingProject = new Project();
        existingProject.setId(5L);
        existingProject.setName("Test Project");
        existingProject.setDescription("Test Description");
        existingProject.setCreatedDate(LocalDate.ofEpochDay(2024-11-24));

        // Настраиваем обновленный проект
        Project updatedProject = new Project();
        updatedProject.setId(5L);
        updatedProject.setName("Test Updated");
        updatedProject.setDescription("Test Description");
        updatedProject.setCreatedDate(LocalDate.ofEpochDay(2023-11-24));

        // Настраиваем projectRepository, чтобы метод findById возвращал existingProject
        // когда ProjectService вызывает findById.
        given(projectRepository.findById(projectId)).willReturn(Optional.of(existingProject));

        // Вызываем метод updateProjectById у сервиса, передав в него projectId и updatedProject
        projectService.updateProjectById(projectId, updatedProject);

        // Проверяем, что поля existingProject обновляются с данными из updatedProject
        assertEquals("Test Updated", existingProject.getName());
        assertEquals("Test Description", existingProject.getDescription());
        assertEquals(LocalDate.ofEpochDay(2023 - 11 - 24), existingProject.getCreatedDate());

        // Проверяем, что репозиторий был вызвалн с правильными параметрами
        verify(projectRepository).findById(projectId);

        // Проверка вызова метода updateProjectById
        verify(projectRepository).updateProjectById(projectId, existingProject);

        // Проверка вызова метода flush
        verify(projectRepository).flush();
    }
}
