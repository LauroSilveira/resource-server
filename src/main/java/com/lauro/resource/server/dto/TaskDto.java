package com.lauro.resource.server.dto;

import com.lauro.resource.server.model.Task;

public record TaskDto(
        long id,
        String title,
        String description) {

    public TaskDto(Task task) {
        this(task.getId(), task.getTitle(), task.getDescription());
    }
}

