package com.lauro.resource.server.controller;

import com.lauro.resource.server.dto.TaskDto;
import com.lauro.resource.server.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
@Slf4j
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/create")
    public Mono<String> createTask(TaskDto taskDto, @AuthenticationPrincipal Jwt jwt) {
        log.info("[TaskController] - Received request to create new task: {}", taskDto.toString());
        this.taskService.create(taskDto);
        return Mono.just("OK: " + jwt.getSubject());
    }
}
