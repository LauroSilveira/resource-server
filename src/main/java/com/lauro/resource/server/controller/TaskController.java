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
        log.info("[TaskController] - User: {} request get all notes", jwt.getSubject());
         return this.taskService.getAllNotes()
                 .map(ResponseEntity::ok);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<TaskDto>> createTask(@RequestBody TaskDto taskDto, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request to create new task: {} with user{}: ", taskDto.toString(), jwt.getSubject());
       return this.taskService.create(taskDto)
                .map(ResponseEntity::ok);
    }
}
