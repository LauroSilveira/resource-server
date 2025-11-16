package com.lauro.resource.server.model;

import com.lauro.resource.server.dto.TaskDto;
import com.lauro.resource.server.dto.TasksDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    public static Task toTask(TaskDto dto) {
        return new Task(null, dto.title(), dto.description());
    }

    public static TaskDto toTaskDto(final Task tasks) {
        return new TaskDto(tasks.id, tasks.title, tasks.description);
    }
}
