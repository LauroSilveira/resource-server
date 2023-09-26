package com.lauro.resource.server.model;

import com.lauro.resource.server.dto.TaskDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "title cannot be null or blank")
    private String title;
    @NotBlank(message = "description cannot be null or blank")
    private String description;

    public static Task toTask(TaskDto dto) {
        return new Task(null, dto.getTitle(), dto.getDescription());
    }
}
