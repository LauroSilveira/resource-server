package com.lauro.resource.server.controller;

import com.lauro.resource.server.dto.TaskDto;
import com.lauro.resource.server.dto.TasksDto;
import com.lauro.resource.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(value = "/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TasksDto>> getAllNotes(@AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to getAllNotes:", jwt.getSubject());
         return this.taskService.getAllNotes()
                 .map(ResponseEntity::ok);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TasksDto>> createTask(@RequestBody TaskDto taskDto, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to create new task: {}:", taskDto.toString(), jwt.getSubject());
       return this.taskService.create(taskDto)
                .map(ResponseEntity::ok);
    }

    @GetMapping(value = "/note/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TaskDto>> getNoteById(@PathVariable String taskId, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to get note: {}", jwt.getSubject(), taskId);
        return this.taskService.getNoteById(taskId)
                .map(ResponseEntity::ok);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TasksDto>> updateTask(@RequestBody TaskDto taskDto, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to update task: {}: ", taskDto.toString(), jwt.getSubject());
        return this.taskService.update(taskDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(value = "/delete/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TaskDto>> deleteTask(@PathVariable String taskId, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to delete note: {}", jwt.getSubject(), taskId);
        return this.taskService.deleteTask(taskId)
                .map(ResponseEntity::ok);
    }
}
