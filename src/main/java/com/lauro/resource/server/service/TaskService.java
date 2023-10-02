package com.lauro.resource.server.service;

import com.lauro.resource.server.dto.TaskDto;
import com.lauro.resource.server.dto.TasksDto;
import com.lauro.resource.server.model.Task;
import com.lauro.resource.server.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }


    public Mono<TasksDto> create(TaskDto taskDto) {
        log.info("[TaskService] - received new task: {}", taskDto);
        try {
            final var model = Task.toTask(taskDto);
            this.repository.save(model);
        }catch (RuntimeException e) {
            log.error("Error to save new Task: {}", e.getMessage());
        }
        return this.getAllNotes();
    }

    public Mono<TasksDto> getAllNotes() {
        log.info("[TaskService] - Request to getAllNotes");
        final var tasks = repository.findAll();
        final var tasksDtoList = Task.toTaskDto(tasks);
        log.info("[TaskService] - Returning tasksDtoList: {}", tasksDtoList);
        return Mono.just(tasksDtoList);
    }


    public Mono<TasksDto> update(TaskDto taskDto) {
        log.info("[TaskService] - received new task: {}", taskDto);
        Optional<Task> taskOptional = this.repository.findById(taskDto.id());
        if (taskOptional.isPresent()) {
            final var taskUpdated = new Task(taskOptional.get().getId(), taskDto.title(), taskDto.description());
            repository.save(taskUpdated);
            return this.getAllNotes();
        } else {
            return Mono.empty();
        }
    }

    public Mono<TaskDto> getNoteById(String taskId) {
        log.info("[TaskService] - retrieving note with ID: {}", taskId);
        Optional<Task> taskOptional = this.repository.findById(Long.valueOf(taskId));
        if (taskOptional.isPresent()) {
            log.info("[TaskService] - Found note: {}", taskOptional);
            return Mono.just(Task.toTaskDto(taskOptional.get()));
        } else {
            log.info("[TaskService] - Not Found note: {}", taskId);
            return Mono.empty();
        }
    }

    public Mono<TaskDto> deleteTask(String taskId) {
        log.info("[TaskService] - retrieving note with ID: {}", taskId);
        Optional<Task> taskOptional = this.repository.findById(Long.valueOf(taskId));
        if (taskOptional.isPresent()) {
            log.info("[TaskService] - Found note: {}", taskOptional);
            this.repository.deleteById(taskOptional.get().getId());
        } else {
            log.info("[TaskService] - Not Found note: {}", taskOptional);
        }
        return Mono.empty();
    }
}
