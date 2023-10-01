package com.lauro.resource.server.service;

import com.lauro.resource.server.dto.TaskDto;
import com.lauro.resource.server.dto.TasksDto;
import com.lauro.resource.server.model.Task;
import com.lauro.resource.server.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Mono<TaskDto> create(TaskDto taskDto) {
        log.info("[TaskService] - received new task: {}", taskDto);
        final var model = Task.toTask(taskDto);
        //TODO: Handle posibles errors
        this.repository.save(model);
        return Mono.just(taskDto);
    }

    public Mono<TasksDto> getAllNotes() {
        log.info("[TaskService] - Request to getAllNotes");
        final var tasks = repository.findAll();
        final var tasksDtoList = Task.toTaskDto(tasks);
        log.info("[TaskService] - Returning tasksDtoList: {}", tasksDtoList);
        return Mono.just(tasksDtoList);
    }
}
