package com.lauro.resource.server.controller;

import com.lauro.resource.server.dto.TaskDto;
import com.lauro.resource.server.dto.TasksDto;
import com.lauro.resource.server.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TasksDto> getAllNotes(@AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to getAllNotes:", jwt.getSubject());
        return ResponseEntity.ok(this.taskService.getAllNotes());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to create new task: {}:", taskDto.toString(), jwt.getSubject());
        final var tasksDto = this.taskService.create(taskDto);
        final var uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/tasks/{id}")
                .buildAndExpand(tasksDto.id()).toUri();
        return ResponseEntity.created(uri).body(tasksDto);
    }

    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> getNoteById(@PathVariable Long taskId, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to get note: {}", jwt.getSubject(), taskId);
        final var taskDto = this.taskService.getNoteById(taskId);
        return ResponseEntity.ok(taskDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TasksDto> updateTask(@RequestBody TaskDto taskDto, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to update task: {}: ", taskDto.toString(), jwt.getSubject());
        final var tasksUpdatedDto = this.taskService.update(taskDto);
        return ResponseEntity.ok().body(tasksUpdatedDto);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request of user: {} to delete note: {}", jwt.getSubject(), id);
        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
