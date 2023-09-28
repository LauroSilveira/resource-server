package com.lauro.resource.server.service;

import com.lauro.resource.server.dto.CreateTaskDto;
import com.lauro.resource.server.model.Task;
import com.lauro.resource.server.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class TaskService {

    private TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Mono<CreateTaskDto> create(CreateTaskDto createTaskDto) {
        log.info("[TaskService] - received new task: {}", createTaskDto);
        final var model = Task.toTask(createTaskDto);
        //TODO: Handle posibles errors
        this.repository.save(model);
        return Mono.just(createTaskDto);
    }
}
