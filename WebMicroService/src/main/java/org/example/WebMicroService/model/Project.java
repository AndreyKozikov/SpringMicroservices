package org.example.WebMicroService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@AllArgsConstructor
@Data
public class Project {
    private Long id;

    private String name;

    private String description;

    private LocalDate createdDate;
}
