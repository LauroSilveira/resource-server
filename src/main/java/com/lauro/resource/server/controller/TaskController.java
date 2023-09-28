package com.lauro.resource.server.controller;

import com.lauro.resource.server.dto.CreateTaskDto;
import com.lauro.resource.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/create")
    public Mono<ResponseEntity<CreateTaskDto>> createTask(@RequestBody CreateTaskDto createTaskDto, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request to create new task: {} with user{}: ", createTaskDto.toString(), jwt.getSubject());
       return this.taskService.create(createTaskDto)
                .map(ResponseEntity::ok);
    }
}
